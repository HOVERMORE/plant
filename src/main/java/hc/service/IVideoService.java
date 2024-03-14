package hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.Video;
import hc.pojos.dto.VideoDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoService extends IService<Video> {
    /**
     * 上传视频
     * @param multipartFile
     * @return
     */
    ResponseResult uploadVideo(MultipartFile multipartFile);

    /**
     * 获取视频链接
     * @param videoId
     * @return
     */
    ResponseResult playVideo(String videoId);

    /**
     * 获取所有视频
     * @return
     */
    ResponseResult getAllVideo();
    /**
     * 每天执行一次删除任务
     */
    void timedDeleteVideo();
}
