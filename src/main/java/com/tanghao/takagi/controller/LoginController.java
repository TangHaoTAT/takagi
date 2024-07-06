package com.tanghao.takagi.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
    @Operation(summary ="发送邮箱验证码或短信验证码")
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
        Boolean status = userInfoService.isOpenCodeAndVerCodeMatch(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        // 若该用户不存在，为其创建账号
        User user = userInfoService.getUserByOpenCode(openCode);
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
        User user = userInfoService.getUserByOpenCode(openCode);
        if (!DigestUtil.md5Hex(password).equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        userInfoService.login(user.getId());
        return CommonResult.ok();
    }

    @PostMapping("/register")
    @Operation(summary ="账号注册")
    public CommonResult register(@RequestBody RegisterVo registerVo) {
        String openCode = registerVo.getOpenCode();
        String password = registerVo.getPassword();
        String verCode = registerVo.getVerCode();
        if (StrUtil.isBlank(openCode) || StrUtil.isBlank(password) || StrUtil.isBlank(verCode)) {
            throw new RuntimeException("账号或密码或验证码不能为空");
        }
        // 校验openCode与verCode是否匹配
        Boolean status = userInfoService.isOpenCodeAndVerCodeMatch(openCode, verCode);
        if (!status) {
            throw new RuntimeException("验证码过期，请重新发送");
        }
        // 若该用户不存在，为其创建账号
        User user = userInfoService.getUserByOpenCode(openCode);
        if (ObjectUtil.isNotNull(user)) {
            throw new RuntimeException("该账号已存在");
        }
        userInfoService.createNewUser(openCode, DigestUtil.md5Hex(password));
        return CommonResult.ok();
    }

    @PostMapping("/logout")
    @Operation(summary ="退出登录")
    public CommonResult logout() {
        userInfoService.logout();
        return CommonResult.ok();
    }
}
