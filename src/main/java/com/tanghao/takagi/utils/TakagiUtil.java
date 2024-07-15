package com.tanghao.takagi.utils;

import cn.hutool.core.util.ObjectUtil;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description 常用工具类
 */
public class TakagiUtil {
    /**
     * 随机生成6位数的验证码
     * @return 6位数的验证码
     */
    public static String createVerCode() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
    }

    /**
     * 随机生成8位数的昵称后缀
     * @return 8位数的昵称后缀
     */
    public static String createNicknameSuffix() {
        String SUFFIX = "abcdefghijkmnpqrstuvwxyz0123456789";
        StringBuilder nicknameSuffix = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            nicknameSuffix.append(SUFFIX.charAt((int) (Math.random() * SUFFIX.length())));
        }
        return nicknameSuffix.toString();
    }

    /**
     * 使用正则表达式来判断一个字符串是否是有效的电子邮箱地址
     * @param email 邮箱地址
     */
    public static boolean verifyValidEmail(String email) {
        if (ObjectUtil.isNull(email)) {
            return false;
        }
        String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(EMAIL_REGEX);
    }

    /**
     * 使用正则表达式来判断一个字符串是否是一个合法的手机号
     * @param phone 手机号
     */
    public static boolean verifyValidChinesePhone(String phone) {
        if (ObjectUtil.isNull(phone)) {
            return false;
        }
        String PHONE_REGEX = "^1[3456789]\\d{9}$";
        return phone.matches(PHONE_REGEX);
    }
}
