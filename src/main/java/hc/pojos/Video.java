package hc.pojos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@Accessors(chain = true)
@TableName("tb_video")
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable  {
    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频Id",required = false)
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户Id",required = false)
    private String userId;
    /**
     * 视频名
     */
    @ApiModelProperty(value = "视频名",required = false)
    private String videoName;
    /**
     * 视频存放地址
     */
    @ApiModelProperty(value = "视频地址",required = false)
    private String videoUrl;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者",required = false)
    private String createUser;
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
