package com.tanghao.takagi.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description XssFilter
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
public class XssFilter implements Filter {
    /**
     * 忽略权限检查的url地址
     */
    private final String[] excludeUrls = new String[]{

    };

    @Override
    public void init(FilterConfig filterConfig1) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求你ip后的全部路径
        String uri = request.getRequestURI();
        //跳过不需要的Xss校验的地址
        for (String str : excludeUrls) {
            if (uri.contains(str)) {
                filterChain.doFilter(servletRequest, response);
                return;
            }
        }
        //注入xss过滤器实例
        XssHttpServletRequestWrapper wrapper = new XssHttpServletRequestWrapper(request);
        //过滤
        filterChain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {

    }
}