package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.User;
import com.tanghao.takagi.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @description 用户Service
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
