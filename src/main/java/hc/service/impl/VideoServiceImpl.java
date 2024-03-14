package hc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.javafx.collections.MappingChange;
import hc.exception.DataException;
import hc.exception.ParamErrorException;
import hc.mapper.VideoMapper;
import hc.pojos.Logs;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.Token;
import hc.pojos.Video;
import hc.pojos.dto.VideoDTO;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.IVideoService;
import hc.util.FileUtil;
import hc.util.constants.VideoConstants;
import hc.util.thread.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static hc.util.constants.VideoConstants.VIDEO_URL;


@Slf4j
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {
    @Resource
    private ILogsService logsService;
    /**
     * 上传视频
     * @param multipartFile
     * @return
     */
    @Override
    public ResponseResult uploadVideo(MultipartFile multipartFile) {
        try {
            multipartFile.isEmpty();
        } catch (Exception ex) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"multipartFile参数缺失");
            logsService.markLogs(HttpCodeEnum.NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "multipartFile参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new ParamErrorException(HttpCodeEnum.NO_FOUND_PARAM);
        }
        String name=multipartFile.getOriginalFilename();
        String subffix=name.substring(name.lastIndexOf(".")+1,name.length());
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String videoFilepath = FileUtil.createFiles(VIDEO_URL);
        boolean isSuccess = FileUtil.saveVideo(multipartFile, videoFilepath+"\\"+fileName + "." + subffix);
        if(isSuccess) {
            String playPath = VideoConstants.PLAY_URL + videoFilepath
                    .replace(VIDEO_URL, "")
                    .replace("\\", "/");
            Video video = new Video().setVideoName(fileName)
                    .setUserId(UserHolder.getUser().getId())
                    .setVideoUrl(playPath)
                    .setCreateUser(UserHolder.getUser().getNickName());
            save(video);
            VideoDTO videoDTO = new VideoDTO();
            BeanUtil.copyProperties(video, videoDTO, true);
            return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(), videoDTO, "视频上传成功");
        }else{
            return new ResponseResult().error(HttpCodeEnum.FILE_UPLOAD_FAIL.getCode(),HttpCodeEnum.FILE_UPLOAD_FAIL.getErrorMsg());
        }
    }
    /**
     * 获取视频链接
     *
     * @param videoId
     * @return
     */
    @Override
    public ResponseResult playVideo(String videoId) {
        if(StrUtil.isBlank(videoId)) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"videoId参数缺失");
            logsService.markLogs(HttpCodeEnum.NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "videoId参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new ParamErrorException(HttpCodeEnum.NO_FOUND_PARAM);
        }
        Video one = getById(videoId);
        if(one == null){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"video未查询到结果");
            logsService.markLogs(HttpCodeEnum.DATA_NOT_EXIST,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "video未查询到结果",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new DataException(HttpCodeEnum.DATA_NOT_EXIST);
        }
        Map<String,String> map=new HashMap<>();
        map.put("videoUrl",one.getVideoUrl());
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),map,"视频链接获取成功");
    }

    /**
     * 获取所有视频
     *
     * @return
     */
    @Override
    public ResponseResult getAllVideo() {
        List<Video> videos = query().orderByAsc("create_time").list();
        List<Map<VideoDTO,Map<String,String>>> videoDTOs=new ArrayList<>();
        VideoDTO videoDTO=new VideoDTO();
        if(!videos.isEmpty()) {
            for (Video v : videos) {
                Map<VideoDTO, Map<String, String>> videoDTOmap = new HashMap<>();
                Map<String, String> map = new HashMap<>();
                map.put("videoUrl", v.getVideoUrl());
                map.put("createTime", String.valueOf(v.getCreateTime()));
                BeanUtil.copyProperties(v, videoDTO, true);
                videoDTOmap.put(videoDTO, map);
                videoDTOs.add(videoDTOmap);
            }
        }
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),videoDTOs,"所有视频获取成功");
    }

    /**
     * 每天执行一次删除任务
     */
    @Override
    @Scheduled(fixedRate = 1000*60*60*24)
    public void timedDeleteVideo() {
        List<String> videoIds = query().lt("create_time", LocalDateTime.now().minusMonths(1)).list()
                .stream().map(Video::getId).collect(Collectors.toList());
        if(!videoIds.isEmpty())
            removeByIds(videoIds);
    }
}
