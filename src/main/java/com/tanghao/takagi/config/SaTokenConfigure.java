package com.tanghao.takagi.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description Sa-Token配置
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    @Value("${file.access-path}")
    private String accessPath;

    @Value("${file.upload-folder}")
    private String uploadFolder;

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
            "/v3/**",
    };

    private static final String[] URL = {
            "/account/createLoginCaptcha",
            "/account/passwordlessLogin",
            "/account/login",
    };

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能WebMvcConfigurer
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(SWAGGER)
                .excludePathPatterns(URL);
    }

    // 配置文件路径映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath)
                .addResourceLocations(uploadFolder);
    }
}

