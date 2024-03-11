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
}
