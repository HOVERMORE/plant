package hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import hc.pojos.Logs;
import hc.pojos.enums.HttpCodeEnum;

public interface ILogsService extends IService<Logs> {
    void markLogs(HttpCodeEnum httpCodeEnum, String serviceName,String msg, String username, String userId);
}
