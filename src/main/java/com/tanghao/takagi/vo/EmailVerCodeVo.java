package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description 获取邮箱验证码请求参数
 */
@Data
@Schema(description = "获取邮箱验证码请求参数")
public class EmailVerCodeVo {
    @NotBlank
    @Email
    @Schema(name = "email", description = "邮箱")
    public String email;
}
