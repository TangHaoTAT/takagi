package com.tanghao.takagi.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @description 登录Controller
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/account")
@Tag(name = "登录")
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/createEmailLoginCaptcha")
    @Operation(summary ="发送邮箱登录验证码")
    public CommonResult createEmailLoginCaptcha(@Validated @RequestBody EmailVerCodeVo emailVerCodeVo) {
        String email = emailVerCodeVo.getEmail();
        userInfoService.createLoginCaptcha(email);
        return CommonResult.ok();
    }

    @PostMapping("/EmailPasswordlessLogin")
    @Operation(summary ="邮箱免密登录")
    public CommonResult EmailPasswordlessLogin(@Validated @RequestBody EmailPasswordlessLoginVo passwordlessLoginInfoVo) {
        String email = passwordlessLoginInfoVo.getEmail();
        String verCode = passwordlessLoginInfoVo.getVerCode();
        // 校验openCode与verCode是否匹配
        Boolean status = userInfoService.verifyLoginCaptcha(email, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        // 若该用户不存在，为其创建账号
        User user = userInfoService.getUser(email);
        if (ObjectUtil.isNull(user)) {
            user = userInfoService.createNewUser(email, null);
        }
        userInfoService.login(user.getId());
        return CommonResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary ="账号密码登录")
    public CommonResult login(@Validated @RequestBody LoginVo loginVo) {
        String openCode = loginVo.getOpenCode();
        String password = loginVo.getPassword();
        User user = userInfoService.getUser(openCode);
        if (ObjectUtil.isNull(user) || !SecureUtil.md5(password).equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        userInfoService.login(user.getId());
        return CommonResult.ok();
    }

    @PostMapping("/logout")
    @Operation(summary ="退出登录")
    public CommonResult logout() {
        userInfoService.logout();
        return CommonResult.ok();
    }

}
