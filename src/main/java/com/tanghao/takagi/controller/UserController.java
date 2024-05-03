package com.tanghao.takagi.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.CommonResult;
import com.tanghao.takagi.vo.LoginInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户Controller")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据openCode发送邮件验证码或手机短信验证码
     * 在Redis中用‘USER_ + openCode’格式的key存储用户相关信息
     * ‘verCode’作为用户信息中验证码的key，查询Redis中用户的验证码信息
     */
    @PostMapping("/login")
    @Operation(summary ="登录")
    public CommonResult login(@RequestBody LoginInfoVo loginInfoVo) {
        if (null == loginInfoVo || null == loginInfoVo.getOpenCode()) {
            throw new RuntimeException("账号不能为空");
        }

        String openCode = loginInfoVo.getOpenCode();
        userInfoService.sendVerCodeByOpenCode(openCode);

        return CommonResult.ok();
    }

    /**
     * 根据openCode和verCode校验并登录，若该用户是第一次登录系统，为其创建账号
     */
    @PostMapping("/checkVerCode")
    @Operation(summary ="校验登录验证码")
    public CommonResult checkVerCode(@RequestBody LoginInfoVo loginInfoVo) {
        if (null == loginInfoVo || null == loginInfoVo.getOpenCode() || null == loginInfoVo.getVerCode()) {
            throw new RuntimeException("账号或验证码不能为空");
        }

        String openCode = loginInfoVo.getOpenCode();
        String verCode = loginInfoVo.getVerCode();
        Boolean status = userInfoService.isOpenCodeAndVerCodeMatch(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        Boolean exists = userInfoService.isAccountExists(openCode);
        if (!exists) {
            userInfoService.insertNewAccount(openCode, null);
        }
        StpUtil.login(openCode);
        userInfoService.refreshUserInfoInRedis(openCode);

        return CommonResult.ok();
    }

    @PostMapping("/logout")
    @Operation(summary ="退出登录")
    public CommonResult logout() {
        StpUtil.logout();
        return CommonResult.ok();
    }
}
