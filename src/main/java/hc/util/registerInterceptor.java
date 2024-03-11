package hc.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import hc.exception.ServerErrorException;
import hc.pojos.Logs;
import hc.pojos.Token;
import hc.pojos.User;
import hc.pojos.enums.HttpCodeEnum;
import hc.service.ILogsService;
import hc.service.ITokenService;
import hc.service.IUserService;
import hc.util.thread.UserHolder;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static hc.pojos.enums.HttpCodeEnum.*;

public class registerInterceptor implements HandlerInterceptor {

    private ITokenService tokenService;
    private IUserService userService;
    private ILogsService logsService;

    public registerInterceptor(ITokenService tokenService, IUserService userService, ILogsService logsService) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.logsService = logsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            return false;
        }
        Token one = tokenService.query().eq("token", token).one();
        //5，判断token是否有效
        try {
            Claims claimsBody = AppJwtUtil.getClaimsBody(token);
            //是否过期
            int result = AppJwtUtil.verifyToken(claimsBody);
            if (result == 1 || result == 2) {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                logsService.markLogs(DATA_FAILURE,
                        this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                        "token已过期",
                        "未知","未知");
                tokenService.removeById(one.getId());
                return false;
            }
        } catch (Exception e) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            logsService.markLogs(JWT_VERIFICATION_ERROR,
                    this.getClass().getSimpleName()+"."+stackTrace[1].getMethodName(),
                    JWT_VERIFICATION_ERROR.getErrorMsg(),
                    "未知","未知");
            throw new ServerErrorException(JWT_VERIFICATION_ERROR);
        }
        User user = userService.getById(one.getUserId());
        UserHolder.setUser(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.clear();
    }
}
