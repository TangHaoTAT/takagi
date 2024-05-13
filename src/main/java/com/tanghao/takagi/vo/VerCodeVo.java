package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 获取手机或邮箱验证码请求参数
 */
@Data
@Schema(description = "获取手机或邮箱验证码请求参数")
public class VerCodeVo {
    @Schema(name = "openCode", description = "邮箱或手机号")
    public String openCode;
}
