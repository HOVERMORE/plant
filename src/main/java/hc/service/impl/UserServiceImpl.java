package hc.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import hc.exception.DataException;
import hc.pojos.Logs;
import hc.pojos.Respeonse.ResponseResult;
import hc.pojos.Token;
import hc.pojos.User;
import hc.mapper.UserMapper;
import hc.pojos.dto.UserDTO;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.ITokenService;
import hc.service.IUserService;
import hc.util.AppJwtUtil;
import hc.util.thread.UserHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private ITokenService tokenService;
    @Resource
    private ILogsService logsService;

    /**
     * 用户登录功能
     *
     * @param userDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseResult login(UserDTO userDTO) {
        User one = query().eq("nick_name", userDTO.getNickName())
                .eq("password", userDTO.getPassword()).one();
        if (one == null) {
            //获取方法名
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName() + "." + stackTrace[1].getMethodName() + ":" + userDTO.getNickName() + "用户不存在");
            logsService.markLogs(HttpCodeEnum.DATA_NOT_EXIST,
                    this.getClass().getSimpleName() + "." + stackTrace[1].getMethodName(),
                    userDTO.getNickName() + "用户不存在",
                    "未知", "未知");
            throw new DataException(HttpCodeEnum.DATA_NOT_EXIST);
        }

        String token = AppJwtUtil.getToken(Long.parseLong(one.getId()));

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", userDTO);
        Token tokenByUserId = tokenService.query().eq("user_id", one.getId()).one();
        if (tokenByUserId != null) {
            tokenByUserId.setToken(token);
            tokenService.updateById(tokenByUserId);
        } else {
            tokenService.save(new Token().setToken(token).setUserId(one.getId()));
        }
        return new ResponseResult().ok(HttpCodeEnum.SUCCESS.getCode(), map, "用户登录成功");
    }

    /**
     * 用户注册功能
     *
     * @param userDTO
     * @return
     */
    @Override
    public ResponseResult register(UserDTO userDTO) {
        User one = query().eq("nick_name", userDTO.getNickName())
                .eq("password", userDTO.getPassword()).one();
        if (one != null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            log.error(this.getClass().getSimpleName() + "." + stackTrace[1].getMethodName() + ":" + userDTO.getNickName() + "用户已注册");
            logsService.markLogs(HttpCodeEnum.DATA_EXIST,
                    this.getClass().getSimpleName() + "." + stackTrace[1].getMethodName(),
                    userDTO.getNickName() + "用户已注册",
                    one.getNickName(), one.getId());

            return ResponseResult.errorResult(HttpCodeEnum.DATA_EXIST, userDTO.getNickName() + "用户已注册");
        }
        User user = new User();
        BeanUtil.copyProperties(userDTO, user, true);
        save(user);
        return ResponseResult.okResult(HttpCodeEnum.SUCCESS.getCode(), "用户注册成功");
    }
}
