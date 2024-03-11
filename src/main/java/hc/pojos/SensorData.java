package hc.pojos;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@ToString
@Accessors(chain = true)
@TableName("tb_sensor")
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    /**
     *传感器Id
     */
    @TableId(type=IdType.ASSIGN_ID)
    @ApiModelProperty(value = "传感器Id",required = false)
    private String id;
    /**
     * 创建者id
     */
    @ApiModelProperty(value="创建者id",required = false)
    private String userId;
    /**
     * 大气温度
     */
    @ApiModelProperty(value = "大气温度",required = true)
    private String atmTemperature;
    /**
     * 大气湿度
     */
    @ApiModelProperty(value="大气湿度",required = true)
    private String atmHumidity;
    /**
     * 土壤温度
     */
    @ApiModelProperty(value="土壤温度", required = true)
    private String soilTemperature;
    /**
     * 土壤湿度
     */
    @ApiModelProperty(value = "土壤湿度",required = true)
    private String soilHumidity;
    /**
     * 光照度
     */
    @ApiModelProperty(value = "光照度",required = true)
    private String illuminance;
    /**
     * 二氧化碳浓度
     */
    @ApiModelProperty(value="二氧化碳浓度",required = true)
    private String carbon;
    /**
     * PH值
     */
    @ApiModelProperty(value = "PH值",required = true)
    private String ph;
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
