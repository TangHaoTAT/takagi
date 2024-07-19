package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.vo.UserInfoVo;
import org.apache.ibatis.annotations.*;

/**
 * @description 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户id获取用户基本信息
     * @param userId 用户id
     */
    @Results(id = "getUserInfoVoById", value = {
            @Result(property = "userId", column = "id", id = true),
            @Result(property = "nickname", column = "nickname"),
            @Result(property = "introduce", column = "introduce"),
            @Result(property = "avatarUrl", column = "avatar_url"),
    })
    @Select({"<script>",
            " select id, nickname, introduce, avatar_url from user ",
            " where 1=1 ",
            " and id = #{userId} ",
            "</script>"})
    UserInfoVo getUserInfoVoByUserId(Long userId);
}
