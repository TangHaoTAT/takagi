package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.Role;
import com.tanghao.takagi.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * @description 角色Service
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
}
