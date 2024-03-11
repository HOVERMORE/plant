package hc.pojos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户类
 */
@Data
@ToString
@TableName("tb_user")
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户Id",required = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称",required = true)
    private String nickName;
    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址",required = false)
    private String avatarUrl;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间",required = false)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间",required = false)
    @TableField(fill= FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除",required = false)
    private Integer isDeleted;
}
