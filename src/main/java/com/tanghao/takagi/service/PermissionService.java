package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.Permission;
import com.tanghao.takagi.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
 * @description 权限Service
 */
@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
}
