package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 用户基本信息编辑
 */
@Data
@Schema(description = "用户基本信息编辑")
public class UserInfoEditVo {
    @Schema(name = "nickname", description = "昵称")
    public String nickname;

    @Schema(name = "introduce", description = "个性签名")
    public String introduce;

}
