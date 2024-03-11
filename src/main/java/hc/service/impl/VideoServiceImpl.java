package hc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.VideoMapper;
import hc.pojos.Logs;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.Video;
import hc.pojos.dto.VideoDTO;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.IVideoService;
import hc.util.constants.VideoConstants;
import hc.util.thread.UserHolder;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Date;


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
        //视频上传
        //获取原文件名
        String name=multipartFile.getOriginalFilename();
        //获取文件后缀
        String subffix=name.substring(name.lastIndexOf(".")+1,name.length());
        //新的文件名以日期命名
        String fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        LocalDateTime currentDate=LocalDateTime.now();
        String year=String.valueOf(currentDate.getYear());
        String month=String.valueOf(currentDate.getMonthValue());
        String filepath= VideoConstants.VIDEO_URL+"\\"+year+"\\"+month;
        Path path = Paths.get(filepath);

        if(!Files.exists(path)){
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"文件创建失败");
                logsService.markLogs(HttpCodeEnum.FILE_CREATE_FAIL,
                        this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                        this.getClass().getSimpleName()+"文件创建失败",
                        UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
                e.printStackTrace();
            }
        }
        String realPath=filepath+"\\"+fileName+"."+subffix;
        try {
            multipartFile.transferTo(new File(realPath));
        } catch (IOException e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"视频上传失败");
            logsService.markLogs(HttpCodeEnum.FILE_UPLOAD_FAIL,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    this.getClass().getSimpleName()+"视频上传失败",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            e.printStackTrace();
        }
        String playPath=VideoConstants.PLAY_URL+realPath
                .replace(VideoConstants.VIDEO_URL,"")
                .replace("\\", "/");
        Video video=new Video().setVideoName(fileName)
                .setUserId(UserHolder.getUser().getId())
                .setVideoUrl(playPath)
                .setCreateUser(UserHolder.getUser().getNickName());
        save(video);
        VideoDTO videoDTO=new VideoDTO();
        BeanUtil.copyProperties(video,videoDTO,true);
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),videoDTO,"视频上传成功");
    }
}
