package com.tanghao.takagi.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description Sa-Token配置
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    //放行路径
    private static final String[] SWAGGER = {
            "**/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-ui.html/**",
            "/doc.html/**",
            "/error",
            "/favicon.ico",
            "sso/auth",
            "/csrf",
            "/swagger-ui/**",
            "/v3/**"
    };

    private static final String[] URLS = {
            "/user/passwordlessLogin",
            "/user/checkPasswordlessLoginVerCode",
            "/user/login",
            "/user/register"
    };

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能WebMvcConfigurer
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(SWAGGER)
                .excludePathPatterns(URLS);
    }
}

