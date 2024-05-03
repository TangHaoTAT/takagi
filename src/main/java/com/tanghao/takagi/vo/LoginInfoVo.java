package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 登录请求参数
 */
@Data
@Schema(description = "登录请求参数")
public class LoginInfoVo {
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;

    @Schema(name = "password", description = "密码")
    public String password;

    @Schema(name = "verCode", description = "验证码")
    public String verCode;
}
