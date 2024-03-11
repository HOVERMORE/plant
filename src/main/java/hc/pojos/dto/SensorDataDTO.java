package hc.pojos.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SensorDataDTO {
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
}
