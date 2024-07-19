package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @description 账号密码登录请求参数
 */
@Data
@Schema(description = "账号密码登录请求参数")
public class LoginVo {
    @NotBlank
    @Pattern(regexp = "^(([a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+)|(1[3-9]\\d{9}))$", message = "请输入有效的邮箱或国内手机号")
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9$@#.]*$", message = "密码只能由字母(大小写)、数字、和特殊字符($@#.)组成")
    @Schema(name = "password", description = "密码")
    public String password;
}