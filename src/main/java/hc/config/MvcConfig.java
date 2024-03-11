package hc.config;


import hc.service.ILogsService;
import hc.service.ITokenService;
import hc.service.IUserService;
import hc.util.registerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private ITokenService tokenService;
    @Resource
    private IUserService userService;
    @Resource
    private ILogsService logsService;

    /**
     * 配置多个拦截器
     * token更新拦截器拦截所有请求，达到每条请求都可以刷新token的目的
     * 登录拦截器拦截特定请求，完成登录校验与拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有的请求
        registry.addInterceptor(new registerInterceptor(tokenService,userService,logsService))
                .excludePathPatterns(
                        "/user/register",
                        "/user/login",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**",
                        "/api",
                        "/api-docs",
                        "/api-docs/**",
                        "/doc.html/**"
                ).order(0);
    }
}
