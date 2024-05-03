package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 权限Mapper
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
