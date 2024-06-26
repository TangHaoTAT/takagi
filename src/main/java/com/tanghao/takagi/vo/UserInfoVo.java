package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description 用户基本信息
 */
@Data
@Schema(description = "用户基本信息")
public class UserInfoVo {
    @Schema(name = "userId", description = "用户id")
    public Long userId;

    @Schema(name = "nickname", description = "昵称")
    public String nickname;

    @Schema(name = "introduce", description = "个性签名")
    public String introduce;

    @Schema(name = "avatarUrl", description = "头像地址")
    public String avatarUrl;

}
