package hc.controller;


import cn.hutool.core.util.StrUtil;
import hc.exception.DataException;
import hc.exception.ParamErrorException;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.Video;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.IVideoService;
import hc.util.thread.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/video")
@Api(value="视频管理",tags="视频管理")
@Slf4j
public class VideoController {

    @Resource
    private IVideoService videoService;
    @Resource
    private ILogsService logsService;

    /**
     * 视频上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    @ApiOperation("视频上传")
    public ResponseResult uploadVideo(MultipartFile multipartFile){
        if(multipartFile.isEmpty()||multipartFile.getSize()==0){
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"multipartFile参数缺失");
            logsService.markLogs(HttpCodeEnum.NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "multipartFile参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new ParamErrorException(HttpCodeEnum.NO_FOUND_PARAM);
         }
        return videoService.uploadVideo(multipartFile);
    }

    /**
     * 播放视频
     * @param videoId
     * @return
     */
    @GetMapping("/playVideo")
    @CrossOrigin(origins = "*")
    @ApiOperation("播放视频")
    public ResponseResult  PlayVideo(@RequestParam String videoId){
        if(StrUtil.isBlank(videoId)) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"videoId参数缺失");
            logsService.markLogs(HttpCodeEnum.NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "videoId参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new ParamErrorException(HttpCodeEnum.NO_FOUND_PARAM);
        }
        Video one = videoService.getById(videoId);
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
        map.put("video_url",one.getVideoUrl());
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),map,"视频链接获取成功");
    }
}
