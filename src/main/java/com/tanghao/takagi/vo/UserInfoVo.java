package com.tanghao.takagi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

/**
 * @description 用户基本信息
 */
@Data
@Schema(description = "用户基本信息")
public class UserInfoVo {
    @Schema(name = "userId", description = "用户id")
    public Long userId;

    @Schema(name = "name", description = "名称")
    public String name;

    @Schema(name = "introduce", description = "个性签名")
    public String introduce;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(name = "birthday", description = "生日")
    public Date birthday;

    @Schema(name = "gender", description = "性别:0=保密,1=男,2=女")
    public Integer gender;

    @Schema(name = "avatarUrl", description = "头像地址")
    public String avatarUrl;

}
