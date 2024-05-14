package com.tanghao.takagi.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.ObjectUtil;
import com.tanghao.takagi.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

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
        String permissionListJson = (String) iGlobalCache.hget(loginId.toString(), "permissionList");
        if (ObjectUtil.isNull(permissionListJson)) {
            return new ArrayList<>();
        }
        return JacksonUtil.convertJsonToList(permissionListJson, String.class);
    }

    /**
     * 返回一个账号所拥有的角色标识集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String roleListJson = (String) iGlobalCache.hget(loginId.toString(), "roleList");
        if (ObjectUtil.isNull(roleListJson)) {
            return new ArrayList<>();
        }
        return JacksonUtil.convertJsonToList(roleListJson, String.class);
    }
}
