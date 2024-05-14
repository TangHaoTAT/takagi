package com.tanghao.takagi.controller;

import com.tanghao.takagi.vo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 用户Controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "用户")
public class UserController {
    @GetMapping("/getCurrentUserInfo")
    @Operation(summary ="获取当前用户信息")
    public CommonResult getCurrentUserInfo() {
        return CommonResult.ok();
    }
}
