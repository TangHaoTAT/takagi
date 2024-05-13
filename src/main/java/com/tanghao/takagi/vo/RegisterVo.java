package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 账号密码注册请求参数
 */
@Data
@Schema(description = "账号密码注册请求参数")
public class RegisterVo {
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;

    @Schema(name = "password", description = "密码")
    public String password;

    @Schema(name = "verCode", description = "验证码")
    public String verCode;
}
