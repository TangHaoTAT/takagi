package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.Permission;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @description 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 获取权限列表
     * @param userId 用户id
     */
    @Results(id = "permissionList", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "code", column = "code"),
            @Result(property = "introduce", column = "introduce"),
    })
    @Select(" select distinct permission.id, permission.deleted, permission.code, permission.introduce from permission " +
            " join role_permission rp on rp.permission_id = permission.id " +
            " where rp.role_id in (select role.id from role join user_role ur on ur.role_id = role.id where ur.user_id = #{userId})")
    List<Permission> listPermissionByUserId(@Param("userId") Long userId);
}
