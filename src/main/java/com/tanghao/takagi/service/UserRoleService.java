package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.UserRole;
import com.tanghao.takagi.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * @description 用户角色Service
 */
@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {
}
