package com.tanghao.takagi.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * @description 验证码工具类
 */
public class VerCodeUtil {
    /**
     * 随机生成6位数的验证码
     */
    public static String generateVerCode() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(1000000));
    }

    /**
     * 使用正则表达式来判断一个字符串是否是有效的电子邮箱地址
     * @param emailAddress 邮箱地址
     */
    public static boolean isValidEmail(String emailAddress) {
        if (null == emailAddress) {
            return false;
        }
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        return EMAIL_PATTERN.matcher(emailAddress).matches();
    }

    /**
     * 使用正则表达式来判断一个字符串是否是一个合法的手机号
     * @param mobileNumber 手机号
     */
    public static boolean isValidChineseMobileNumber(String mobileNumber) {
        if (null == mobileNumber) {
            return false;
        }
        String regex = "^1\\d{10}$";
        return mobileNumber.matches(regex);
    }

}
