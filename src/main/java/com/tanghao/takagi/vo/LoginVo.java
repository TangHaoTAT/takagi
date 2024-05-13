package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 账号密码登录请求参数
 */
@Data
@Schema(description = "账号密码登录请求参数")
public class LoginVo {
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;

    @Schema(name = "password", description = "密码")
    public String password;
}