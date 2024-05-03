package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
