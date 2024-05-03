package com.tanghao.takagi.dto;

import com.tanghao.takagi.entity.Permission;
import com.tanghao.takagi.entity.Role;
import com.tanghao.takagi.entity.User;
import lombok.Data;
import java.util.List;

/**
 * @description 用户信息
 */
@Data
public class UserInfoDto {
    private User user;// 用户

    private List<Role> roleList;// 角色列表

    private List<Permission> permissionList;// 权限列表

}
