package com.tanghao.takagi.config;

import cn.dev33.satoken.exception.*;
import com.tanghao.takagi.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class IGlobalExceptionHandler {
    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public CommonResult handlerException(NotLoginException e) {
        log.error("exception message", e);
        return CommonResult.error(e.getMessage());
    }

    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public CommonResult handlerException(NotPermissionException e) {
        log.error("exception message", e);
        return CommonResult.error("缺少权限：" + e.getPermission());
    }

    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public CommonResult handlerException(NotRoleException e) {
        log.error("exception message", e);
        return CommonResult.error("缺少角色：" + e.getRole());
    }

    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public CommonResult handlerException(NotSafeException e) {
        log.error("exception message", e);
        return CommonResult.error("二级认证校验失败：" + e.getService());
    }

    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public CommonResult handlerException(DisableServiceException e) {
        log.error("exception message", e);
        return CommonResult.error("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }

    // 拦截：Http Basic 校验失败异常
    @ExceptionHandler(NotBasicAuthException.class)
    public CommonResult handlerException(NotBasicAuthException e) {
        log.error("exception message", e);
        return CommonResult.error(e.getMessage());
    }

    // 拦截：其它所有异常
    @ExceptionHandler(Exception.class)
    public CommonResult handlerException(Exception e) {
        log.error("exception message", e);
        return CommonResult.error(e.getMessage());
    }

}
