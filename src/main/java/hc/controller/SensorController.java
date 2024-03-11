package hc.controller;

import cn.hutool.core.bean.BeanUtil;
import hc.exception.ParamErrorException;
import hc.pojos.Logs;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.SensorData;
import hc.pojos.dto.SensorDataDTO;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.ISensorDataService;
import hc.util.thread.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static hc.pojos.enums.HttpCodeEnum.NO_FOUND_PARAM;

@RestController
@RequestMapping("/sensor")
@Api(value = "传感器数据", tags = "传感器管理")
@Slf4j
public class SensorController {
    @Resource
    private ISensorDataService sensorDataService;
    @Resource
    private ILogsService logsService;

    /**
     * 传感器数据采集
     * @param sensorDataDTO
     * @return
     */
    @PostMapping("/gather")
    @CrossOrigin(origins = "*")
    @ApiOperation("传感器数据采集")
    public ResponseResult login(@RequestBody SensorDataDTO sensorDataDTO) {
        if (sensorDataDTO == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName()+":"+"sensorData参数缺失");
            logsService.markLogs(NO_FOUND_PARAM,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    "sensorData参数缺失",
                    UserHolder.getUser().getNickName(), UserHolder.getUser().getId());
            throw new ParamErrorException(NO_FOUND_PARAM);
        }
        SensorData sensorData=new SensorData();
        BeanUtil.copyProperties(sensorDataDTO,sensorData,true);
        sensorData.setCreateUser(UserHolder.getUser().getNickName());
        sensorDataService.save(sensorData);
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(),sensorDataDTO,"传感器数据获取成功");
    }
}
