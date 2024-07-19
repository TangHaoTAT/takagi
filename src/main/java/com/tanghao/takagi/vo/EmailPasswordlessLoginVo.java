package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description 邮箱免密登录请求参数
 */
@Data
@Schema(description = "邮箱免密登录请求参数")
public class EmailPasswordlessLoginVo {
    @Email
    @NotBlank
    @Schema(name = "email", description = "邮箱")
    public String email;

    @NotBlank
    @Schema(name = "verCode", description = "验证码")
    public String verCode;
}