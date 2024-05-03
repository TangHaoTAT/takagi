package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description 用户角色
 */
@Data
@TableName("user_role")
public class UserRole {
    @TableId(type = IdType.AUTO)
    private Long id;// 用户角色id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private Long userId;// 用户id

    private Long roleId;// 角色id
}
