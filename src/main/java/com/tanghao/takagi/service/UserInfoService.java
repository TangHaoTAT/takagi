package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tanghao.takagi.config.IGlobalCache;
import com.tanghao.takagi.dto.UserInfoDto;
import com.tanghao.takagi.entity.Permission;
import com.tanghao.takagi.entity.Role;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.entity.UserRole;
import com.tanghao.takagi.utils.JacksonUtil;
import com.tanghao.takagi.utils.MailUtil;
import com.tanghao.takagi.utils.VerCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 用户信息Service
 */
@Slf4j
@Service
public class UserInfoService {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private IGlobalCache iGlobalCache;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 根据openCode发送邮件验证码或手机短信验证码
     * @param openCode 邮箱或手机号
     */
    public void sendVerCodeByOpenCode(String openCode) {
        String verCode = VerCodeUtil.generateVerCode();
        iGlobalCache.hset(openCode, "verCode", verCode, 600L);
        if (VerCodeUtil.isValidEmail(openCode)) {
            Context context = new Context();
            context.setVariable("verCode", Arrays.asList(verCode.split("")));
            String text = templateEngine.process("EmailVerCode", context);
            mailUtil.sendMailMessage(Arrays.asList(openCode).toArray(new String[0]), "注册验证码", text, true, null);
            log.info("邮件验证码已发送至：" + openCode);
        }
        if (VerCodeUtil.isValidChineseMobileNumber(openCode)) {
            log.info("短信验证码已发送至：");
        }
    }

    /**
     * 校验openCode和verCode是否匹配
     * @param openCode 邮箱或手机号
     * @param verCode 验证码
     */
    public Boolean isOpenCodeAndVerCodeMatch(String openCode, String verCode) {
        Object verCodeInRedis = iGlobalCache.hget(openCode, "verCode");
        if (verCode.equals(verCodeInRedis)) {
            iGlobalCache.hdel(openCode, "verCode");
            return true;
        }
        return false;
    }

    /**
     * 根据openCode查询该用户是否存在
     * @param openCode 邮箱或手机号
     */
    public Boolean isAccountExists(String openCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email_address", openCode).or().eq("mobile_number", openCode);
        User user = userService.getOne(queryWrapper);
        return null != user;
    }

    /**
     * 根据openCode为新用户注册一个账号
     * @param openCode 邮箱或手机号
     */
    @Transactional
    public void insertNewAccount(String openCode, String password) {
        User user = new User();
        user.setDeleted(false);
        userService.save(user);
        user.setName("用户" + user.getId());
        if (VerCodeUtil.isValidEmail(openCode)) {
            user.setEmailAddress(openCode);
        }
        if (VerCodeUtil.isValidChineseMobileNumber(openCode)) {
            user.setMobileNumber(openCode);
        }
        user.setPassword(password);
        userService.saveOrUpdate(user);
        UserRole userRole = new UserRole();
        userRole.setDeleted(false);
        userRole.setUserId(user.getId());
        userRole.setRoleId(1L);// LV0萌新
        userRoleService.save(userRole);
    }

    /**
     * 刷新Redis中的用户信息，主要是用户基本信息以及角色、权限
     * @param openCode 邮箱或手机号
     */
    public void refreshUserInfoInRedis(String openCode) {
        Map<String, Object> userInfo = new HashMap<>();
        UserInfoDto userInfoDto = userService.getBaseMapper().getUserInfoDtoByOpenCode(openCode);
        if (null != userInfoDto) {
            User user = userInfoDto.getUser();
            List<Role> roleList = userInfoDto.getRoleList();
            List<String> roles = roleList.stream().map(Role::getCode).toList();
            List<Permission> permissionList = userInfoDto.getPermissionList();
            List<String> permissions = permissionList.stream().map(Permission::getCode).toList();
            userInfo.put("userId", user.getId());
            userInfo.put("name", user.getName());
            userInfo.put("roleList", JacksonUtil.convertObjectToJson(roles));
            userInfo.put("permissionList", JacksonUtil.convertObjectToJson(permissions));
        }
        iGlobalCache.hmset(openCode, userInfo);
    }
}
