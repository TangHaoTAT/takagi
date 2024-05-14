package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * @description 用户
 */
@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;// 用户id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String name;// 名称

    private String mobileNumber;// 手机号

    private String emailAddress;// 邮箱

    private String password;// 登录密码

    public String introduce;// 个性签名

    public Date birthday;// 生日

    public Integer gender;// 性别:0=保密,1=男,2=女

    public String avatarUrl;//头像地址

}
