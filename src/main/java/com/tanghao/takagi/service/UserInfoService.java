package com.tanghao.takagi.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tanghao.takagi.config.IGlobalCache;
import com.tanghao.takagi.entity.Permission;
import com.tanghao.takagi.entity.Role;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.utils.JacksonUtil;
import com.tanghao.takagi.utils.MailUtil;
import com.tanghao.takagi.utils.TakagiUtil;
import com.tanghao.takagi.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.*;

/**
 * @description 用户信息Service
 */
@Slf4j
@Service
public class UserInfoService {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private IGlobalCache iGlobalCache;

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * 根据openCode发送邮件验证码或手机短信验证码
     * 在Redis中存储验证码信息，以"LOGIN_" + openCode的值为key，‘verCode’为field
     * @param openCode 邮箱或手机号
     */
    public void createLoginCaptcha(String openCode) {
        String verCode = TakagiUtil.createVerCode();
        iGlobalCache.hset("LOGIN_" + openCode, "verCode", verCode, 600L);
        if (TakagiUtil.verifyValidEmail(openCode)) {
            Context context = new Context();
            context.setVariable("verCode", ListUtil.toList(verCode.split("")));
            String text = templateEngine.process("EmailVerCode", context);
            String[] to = {openCode};
            mailUtil.sendMailMessage(to, verCode + " 是你的 Takagi 验证码", text, true, null);
            log.info("邮箱验证码已发送至：" + openCode);
        }
        if (TakagiUtil.verifyValidChinesePhone(openCode)) {
            log.info("短信验证码已发送至：");
        }
    }

    /**
     * 校验登录验证码
     * @param openCode 邮箱或手机号
     * @param verCode 验证码
     */
    public Boolean verifyLoginCaptcha(String openCode, String verCode) {
        Object verCodeInRedis = iGlobalCache.hget("LOGIN_" + openCode, "verCode");
        if (verCode.equals(verCodeInRedis)) {
            iGlobalCache.hdel("LOGIN_" + openCode, "verCode");
            return true;
        }
        return false;
    }

    /**
     * 根据openCode获取用户
     * @param openCode 邮箱或手机号
     */
    public User getUserByOpenCode(String openCode) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getDeleted, false)
                .and(i -> i.and(j -> j.eq(User::getEmail, openCode).or().eq(User::getPhone, openCode)));
        return userService.getOne(queryWrapper);
    }

    /**
     * 根据openCode和password注册新用户
     * @param openCode 邮箱或手机号
     * @param password 密码
     */
    @Transactional
    public User createNewUser(String openCode, String password) {
        String nickname = "用户" + TakagiUtil.createNicknameSuffix();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getNickname, nickname);
        while (userService.exists(queryWrapper)) {
            nickname = "用户" + TakagiUtil.createNicknameSuffix();
            queryWrapper.lambda()
                    .eq(User::getNickname, nickname);
        }
        User user = new User();
        user.setNickname(nickname);
        if (TakagiUtil.verifyValidEmail(openCode)) {
            user.setEmail(openCode);
        }
        if (TakagiUtil.verifyValidChinesePhone(openCode)) {
            user.setPhone(openCode);
        }
        user.setPassword(password);
        user.setCreateDate(DateUtil.date(System.currentTimeMillis()));
        userService.save(user);
        return user;
    }

    /**
     * 退出登录
     */
    public void logout() {
        StpUtil.logout();
    }

    /**
     * 登录
     * @param userId 用户id
     */
    public void login(Long userId) {
        refreshUserInfoInRedis(userId);
        StpUtil.login(userId);
    }

    /**
     * 刷新Redis中的用户信息(用户id、角色、权限）
     * @param userId 用户id
     */
    public void refreshUserInfoInRedis(Long userId) {
        Map<String, Object> userInfo = new HashMap<>();
        User user = userService.getBaseMapper().selectById(userId);
        if (ObjectUtil.isNotNull(user)) {
            List<Role> roleList = roleService.getBaseMapper().listRoleByUserId(userId);
            List<String> roles = roleList.stream().map(Role::getCode).toList();
            List<Permission> permissionList = permissionService.getBaseMapper().listPermissionByUserId(userId);
            List<String> permissions = permissionList.stream().map(Permission::getCode).toList();
            userInfo.put("userId", user.getId());
            userInfo.put("roleList", JacksonUtil.convertObjectToJson(roles));
            userInfo.put("permissionList", JacksonUtil.convertObjectToJson(permissions));
            iGlobalCache.hmset(userId + "", userInfo);
        }
    }

    /**
     * 获取当前用户角色
     */
    public List<String> listCurrentUserRole() {
        String roleListJson = (String) iGlobalCache.hget(StpUtil.getLoginIdAsString(), "roleList");
        if (ObjectUtil.isNull(roleListJson)) {
            return new ArrayList<>();
        }
        return JacksonUtil.convertJsonToList(roleListJson, String.class);
    }

    /**
     * 获取当前用户权限
     */
    public List<String> listCurrentUserPermission() {
        String permissionListJson = (String) iGlobalCache.hget(StpUtil.getLoginIdAsString(), "permissionList");
        if (ObjectUtil.isNull(permissionListJson)) {
            return new ArrayList<>();
        }
        return JacksonUtil.convertJsonToList(permissionListJson, String.class);
    }


    /**
     * 获取当前用户信息
     */
    public UserInfoVo getCurrentUserInfo() {
        return userService.getBaseMapper().getUserInfoVoById(StpUtil.getLoginIdAsLong());
    }

    /**
     * 更新当前用户信息
     * @param nickname 昵称
     * @param introduce 个性签名
     */
    @Transactional
    public void updateCurrentUserInfo(String nickname, String introduce) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(User::getNickname, nickname)
                .ne(User::getId, StpUtil.getLoginIdAsLong());
        if (userService.exists(queryWrapper)) {
            throw new RuntimeException("该昵称已存在");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(User::getId, StpUtil.getLoginIdAsLong());
        User user = new User();
        user.setNickname(nickname);
        user.setIntroduce(introduce);
        userService.update(user, updateWrapper);
    }

    /**
     * 更新当前用户头像
     * @param avatarUrl 图片路径
     */
    public void updateUserAvatar(String avatarUrl) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(User::getId, StpUtil.getLoginIdAsLong());
        User user = new User();
        user.setAvatarUrl(avatarUrl);
        userService.update(user, updateWrapper);
    }

}
