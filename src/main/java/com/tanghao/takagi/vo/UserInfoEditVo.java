package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @description 用户基本信息编辑
 */
@Data
@Schema(description = "用户基本信息编辑")
public class UserInfoEditVo {
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[\\w\\u4e00-\\u9fa5-_]+$", message = "昵称不可包含除-和_以外的特殊字符")
    @Schema(name = "nickname", description = "昵称")
    public String nickname;

    @Size(max = 100)
    @Schema(name = "introduce", description = "个性签名")
    public String introduce;

}
