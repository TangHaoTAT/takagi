package com.tanghao.takagi.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description 登录Controller
 */
@Slf4j
@RestController
@RequestMapping("/account")
@Tag(name = "登录")
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/createLoginCaptcha")
    @Operation(summary ="发送邮箱或短信登录验证码")
    public CommonResult createLoginCaptcha(@RequestBody VerCodeVo verCodeVo) {
        String openCode = verCodeVo.getOpenCode();
        if (StrUtil.isBlank(openCode)) {
            throw new RuntimeException("账号不能为空");
        }
        userInfoService.createLoginCaptcha(openCode);
        return CommonResult.ok();
    }

    @PostMapping("/passwordlessLogin")
    @Operation(summary ="手机或邮箱免密登录")
    public CommonResult passwordlessLogin(@RequestBody PasswordlessLoginVo passwordlessLoginInfoVo) {
        String openCode = passwordlessLoginInfoVo.getOpenCode();
        String verCode = passwordlessLoginInfoVo.getVerCode();
        if (StrUtil.isBlank(openCode) || StrUtil.isBlank(verCode)) {
            throw new RuntimeException("账号或验证码不能为空");
        }
        // 校验openCode与verCode是否匹配
        Boolean status = userInfoService.verifyLoginCaptcha(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        // 若该用户不存在，为其创建账号
        User user = userInfoService.getUser(openCode);
        if (ObjectUtil.isNull(user)) {
            user = userInfoService.createNewUser(openCode, null);
        }
        userInfoService.login(user.getId());
        return CommonResult.ok();
    }

    @PostMapping("/login")
    @Operation(summary ="账号密码登录")
    public CommonResult login(@RequestBody LoginVo loginVo) {
        String openCode = loginVo.getOpenCode();
        String password = loginVo.getPassword();
        if (StrUtil.isBlank(openCode) || StrUtil.isBlank(password)) {
            throw new RuntimeException("账号或密码不能为空");
        }
        User user = userInfoService.getUser(openCode);
        if (!SecureUtil.md5(password).equals(user.getPassword())) {
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
