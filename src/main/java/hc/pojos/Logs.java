package hc.pojos;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@TableName("tb_logs")
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Logs {
    /**
     * 日志id
     */
    private String id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 日志类型
     */
    private String type;
    /**
     * 日志类型代码
     */
    private Integer code;
    /**
     * 问题服务
     */
    private String service;
    /**
     * 创建者
     */
    private String createUser;
    /**
     * 创建时间
     */
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(fill=FieldFill.UPDATE)
    private LocalDateTime updateTime;
    /**
     * 逻辑删除
     */
    private Integer isDeleted;
}
