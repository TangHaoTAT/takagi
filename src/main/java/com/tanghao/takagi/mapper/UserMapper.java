package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.dto.UserInfoDto;
import com.tanghao.takagi.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @description 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 获取用户信息汇总
     * @param openCode 登录账号
     */
    @Results(id = "userInfoDto", value = {
            @Result(property = "user", column = "id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserByUserId")),
            @Result(property = "roleList", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.RoleMapper.listRoleByUserId")),
            @Result(property = "permissionList", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.PermissionMapper.listPermissionByUserId"))
    })
    @Select(" select id from user where deleted = false and (mobile_number = #{openCode} or email_address = #{openCode}) ")
    UserInfoDto getUserInfoDtoByOpenCode(@Param("openCode") String openCode);

    /**
     * 获取用户基本信息
     * @param userId 用户id
     */
    @Results(id = "user", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "name", column = "name"),
            @Result(property = "mobileNumber", column = "mobile_number"),
            @Result(property = "emailAddress", column = "email_address"),
            @Result(property = "password", column = "password"),

    })
    @Select(" select id, deleted, name, mobile_number, email_address, password from user where id = #{userId} ")
    User getUserByUserId(@Param("userId") Long userId);
}
