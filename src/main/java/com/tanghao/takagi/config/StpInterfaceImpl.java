package com.tanghao.takagi.config;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description 自定义权限加载接口实现
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private IGlobalCache iGlobalCache;
    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Set<Object> permissionSet = iGlobalCache.sGet("permission");
        if (null == permissionSet) {
            return new ArrayList<>();
        }
        return permissionSet.stream().map(Object::toString).collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Set<Object> roleSet = iGlobalCache.sGet("role");
        if (null == roleSet) {
            return new ArrayList<>();
        }
        return roleSet.stream().map(Object::toString).collect(Collectors.toList());
    }
}
