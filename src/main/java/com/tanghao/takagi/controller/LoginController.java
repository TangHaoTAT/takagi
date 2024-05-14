package com.tanghao.takagi.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.utils.SecureUtil;
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
@RequestMapping("/user")
@Tag(name = "登录")
public class LoginController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据openCode发送邮件验证码或手机短信验证码
     */
    @PostMapping("/passwordlessLogin")
    @Operation(summary ="手机或邮箱免密登录-发送登录验证码")
    public CommonResult passwordlessLogin(@RequestBody VerCodeVo verCodeVo) {
        if (null == verCodeVo || null == verCodeVo.getOpenCode()) {
            throw new RuntimeException("账号不能为空");
        }

        String openCode = verCodeVo.getOpenCode();
        userInfoService.sendVerCodeByOpenCode(openCode);

        return CommonResult.ok();
    }

    /**
     * 根据openCode和verCode校验并登录
     * 若该用户是第一次登录系统，为其创建账号
     */
    @PostMapping("/checkPasswordlessLoginVerCode")
    @Operation(summary ="手机或邮箱免密登录-校验登录验证码")
    public CommonResult checkPasswordlessLoginVerCode(@RequestBody PasswordlessLoginVo passwordlessLoginInfoVo) {
        if (null == passwordlessLoginInfoVo || null == passwordlessLoginInfoVo.getOpenCode() || null == passwordlessLoginInfoVo.getVerCode()) {
            throw new RuntimeException("账号或验证码不能为空");
        }

        String openCode = passwordlessLoginInfoVo.getOpenCode();
        String verCode = passwordlessLoginInfoVo.getVerCode();
        Boolean status = userInfoService.isOpenCodeAndVerCodeMatch(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        User user = userInfoService.getUserByOpenCode(openCode);
        if (null == user) {
            user = userInfoService.insertNewAccount(openCode, null);
        }
        Long userId = user.getId();
        userInfoService.refreshUserInfoInRedis(userId);
        String loginId = userId + "";
        StpUtil.login(loginId);

        return CommonResult.ok();
    }

    /**
     * 根据账号密码登录
     */
    @PostMapping("/login")
    @Operation(summary ="账号密码登录")
    public CommonResult login(@RequestBody LoginVo loginVo) {
        if (null == loginVo || null == loginVo.getOpenCode() || null == loginVo.getPassword()) {
            throw new RuntimeException("账号或密码不能为空");
        }

        String openCode = loginVo.getOpenCode();
        String password = loginVo.getPassword();
        User user = userInfoService.getUserByOpenCode(openCode);
        if (null == user || !SecureUtil.md5(password).equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        Long userId = user.getId();
        userInfoService.refreshUserInfoInRedis(userId);
        String loginId = userId + "";
        StpUtil.login(loginId);

        return CommonResult.ok();
    }

    /**
     * 根据账号、密码、验证码注册账号
     */
    @PostMapping("/register")
    @Operation(summary ="账号密码注册")
    public CommonResult register(@RequestBody RegisterVo registerVo) {
        if (null == registerVo || null == registerVo.getOpenCode() || null == registerVo.getPassword() || null == registerVo.getVerCode()) {
            throw new RuntimeException("账号或密码或验证码不能为空");
        }

        String openCode = registerVo.getOpenCode();
        String password = registerVo.getPassword();
        String verCode = registerVo.getVerCode();
        Boolean status = userInfoService.isOpenCodeAndVerCodeMatch(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        User user = userInfoService.getUserByOpenCode(openCode);
        if (null != user) {
            throw new RuntimeException("该账号已存在");
        }
        user = userInfoService.insertNewAccount(openCode, SecureUtil.md5(password));

        return CommonResult.ok();
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @Operation(summary ="退出登录")
    public CommonResult logout() {
        StpUtil.logout();
        return CommonResult.ok();
    }
}
