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
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/video")
@Api(value="视频管理",tags="视频管理")
@CrossOrigin(origins = "*")
@Slf4j
public class VideoController {

    @Resource
    private IVideoService videoService;

    /**
     * 视频上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("视频上传")
    public ResponseResult uploadVideo(@ApiParam(value = "上传文件", required = true)MultipartFile multipartFile){
        return videoService.uploadVideo(multipartFile);
    }

    /**
     * 获取视频链接
     * @param videoId
     * @return
     */
    @GetMapping("/playVideo")
    @ApiOperation("获取视频链接")
    public ResponseResult  PlayVideo(@RequestParam String videoId){
        return videoService.playVideo(videoId);
    }
    @GetMapping("/getAllVideo")
    @ApiOperation("获取所有视频")
    public ResponseResult getAllVideo(){
        return videoService.getAllVideo();
    }
}
