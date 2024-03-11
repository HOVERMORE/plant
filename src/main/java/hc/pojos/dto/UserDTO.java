package hc.pojos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDTO {

    @ApiModelProperty(value = "用户昵称",required = true)
    private String nickName;

    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
