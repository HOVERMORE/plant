package hc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.mapper.LogsMapper;
import hc.pojos.Logs;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import org.springframework.stereotype.Service;

@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements ILogsService {
    @Override
    public void markLogs(HttpCodeEnum httpCodeEnum, String serviceName,String msg, String username, String userId) {
        String type=httpCodeEnum.getErrorMsg() + "---" + msg;
        Logs logs = query().eq("user_id",userId)
                .eq("service",serviceName).
                eq("type",type).one();
        if(logs == null) {
            logs = new Logs()
                    .setUserId(userId)
                    .setCode(httpCodeEnum.getCode())
                    .setService(serviceName)
                    .setType(type)
                    .setCreateUser(username);
            save(logs);
        }else{
            updateById(logs);
        }
    }
}
