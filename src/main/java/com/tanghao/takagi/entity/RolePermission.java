package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description 角色权限
 */
@Data
@TableName("role_permission")
public class RolePermission {
    @TableId(type = IdType.AUTO)
    private Long id;// 角色权限id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private Long roleId;// 角色id

    private Long permissionId;// 权限id
}
