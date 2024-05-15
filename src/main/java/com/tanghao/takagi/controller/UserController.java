package com.tanghao.takagi.controller;

import com.tanghao.takagi.service.UserInfoService;
import com.tanghao.takagi.vo.CommonResult;
import com.tanghao.takagi.vo.UserInfoVo;
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
@Tag(name = "用户")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/getCurrentUserInfo")
    @Operation(summary ="获取当前用户信息")
    public CommonResult<UserInfoVo> getCurrentUserInfo() {
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo();
        return CommonResult.data(userInfoVo);
    }
}
