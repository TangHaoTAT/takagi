package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * @description 邮箱免密登录请求参数
 */
@Data
@Schema(description = "邮箱免密登录请求参数")
public class EmailPasswordlessLoginVo {
    @NotBlank
    @Email
    @Schema(name = "email", description = "邮箱")
    public String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{6}$", message = "请输入6位数字")
    @Schema(name = "verCode", description = "验证码")
    public String verCode;
}