package hc.pojos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("tb_gpt_api")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GptApi {
    /**
     * 会话id
     */
    @ApiModelProperty(value = "会话id",required = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id",required = false)
    private String userId;
    /**
     * 用户会话文档
     */
    @ApiModelProperty(value = "用户会话文档",required = false)
    private String userFileUrl;
    /**
     * gpt会话文档
     */
    @ApiModelProperty(value = "gpt会话文档",required = false)
    private String gptFileUrl;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者",required = false)
    private String createUser;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",required = false)
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间",required = false)
    @TableField(fill=FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除",required = false)
    private Integer isDelete;
}
