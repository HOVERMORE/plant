package hc.pojos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;
@Data
@Accessors(chain = true)
public class VideoDTO {

    @ApiModelProperty(value = "视频Id",required = false)
    private String id;

    @ApiModelProperty(value = "视频名称",required = false)
    private String videoName;

}
