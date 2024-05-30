package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description 用户
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;// 用户id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String nickname;// 昵称

    private String phone;// 手机号

    private String email;// 邮箱

    private String password;// 登录密码

    public String introduce;// 个性签名

    public String avatarUrl;//头像路径

}
