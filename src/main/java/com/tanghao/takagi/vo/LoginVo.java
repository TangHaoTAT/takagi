package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description 账号密码登录请求参数
 */
@Data
@Schema(description = "账号密码登录请求参数")
public class LoginVo {
    @NotBlank
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;

    @NotBlank
    @Schema(name = "password", description = "密码")
    public String password;
}