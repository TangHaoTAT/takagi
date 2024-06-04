package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.Role;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @description 角色Mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 获取角色列表
     * @param userId 用户id
     */
    @Results(id = "roleList", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "code", column = "code"),
            @Result(property = "introduce", column = "introduce"),
    })
    @Select({"<script>",
            " select role.id, role.deleted, role.code, role.introduce from role ",
            " join user_role ur on ur.role_id = role.id ",
            " where 1=1 ",
            " and ur.user_id = #{userId} ",
            "</script>"})
    List<Role> listRoleByUserId(@Param("userId") Long userId);
}
