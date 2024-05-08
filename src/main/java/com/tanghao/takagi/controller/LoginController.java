package com.tanghao.takagi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.CommonResult;
import com.tanghao.takagi.vo.PasswordlessLoginInfoVo;
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
     * 在Redis中以openCode为key存储用户相关信息
     * ‘verCode’作为用户信息中验证码的key，查询Redis中用户的验证码信息
     */
    @PostMapping("/passwordlessLogin")
    @Operation(summary ="手机或邮箱免密登录-发送登录验证码")
    public CommonResult passwordlessLogin(@RequestBody PasswordlessLoginInfoVo passwordlessLoginInfoVo) {
        if (null == passwordlessLoginInfoVo || null == passwordlessLoginInfoVo.getOpenCode()) {
            throw new RuntimeException("账号不能为空");
        }

        String openCode = passwordlessLoginInfoVo.getOpenCode();
        userInfoService.sendVerCodeByOpenCode(openCode);

        return CommonResult.ok();
    }

    /**
     * 根据openCode和verCode校验并登录，若该用户是第一次登录系统，为其创建账号
     */
    @PostMapping("/checkPasswordlessLoginVerCode")
    @Operation(summary ="手机或邮箱免密登录-校验登录验证码")
    public CommonResult checkPasswordlessLoginVerCode(@RequestBody PasswordlessLoginInfoVo passwordlessLoginInfoVo) {
        if (null == passwordlessLoginInfoVo || null == passwordlessLoginInfoVo.getOpenCode() || null == passwordlessLoginInfoVo.getVerCode()) {
            throw new RuntimeException("账号或验证码不能为空");
        }

        String openCode = passwordlessLoginInfoVo.getOpenCode();
        String verCode = passwordlessLoginInfoVo.getVerCode();
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

    @SaCheckLogin
    @PostMapping("/logout")
    @Operation(summary ="退出登录")
    public CommonResult logout() {
        StpUtil.logout();
        return CommonResult.ok();
    }
}
