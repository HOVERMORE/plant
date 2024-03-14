package hc.pojos.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GptDTO {
    private String id;
    @ApiModelProperty(value = "用户名",required = true)
    private String nickName;
    @ApiModelProperty(value = "用户内容",required = true)
    private String userContent;
    @ApiModelProperty(value = "gpt内容",required = true)
    private String gptContent;
    @ApiModelProperty(value = "创建时间",required = true)
    private LocalDateTime time;
}
