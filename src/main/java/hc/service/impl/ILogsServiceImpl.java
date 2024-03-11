package hc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.LogsMapper;
import hc.pojos.Logs;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.util.thread.UserHolder;
import org.springframework.stereotype.Service;

@Service
public class ILogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {
    @Override
    public void markLogs(HttpCodeEnum httpCodeEnum, String serviceName,String msg, String username, String userId) {
        Logs logs=new Logs()
                .setUserId(userId)
                .setCode(httpCodeEnum.getCode())
                .setService(serviceName)
                .setType(httpCodeEnum.getErrorMsg()+"---"+msg)
                .setCreateUser(username);
        save(logs);
    }
}
