package com.tanghao.takagi.utils;

import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * @description 常用工具类
 */
public class TakagiUtil {
    /**
     * md5加密
     * @param str 指定字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        str = (str == null ? "" : str);
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] btInput = str.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] strA = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                strA[k++] = hexDigits[byte0 >>> 4 & 0xf];
                strA[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(strA);
        } catch (Exception e) {
            throw new RuntimeException("md5加密异常");
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return 6位数的验证码
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

    /**
     * 判断字符串中是否全是空白字符
     * @param cs 需要判断的字符串
     * @return 如果字符串序列是 null 或者全是空白，返回 true
     */
    public static boolean isBlank(CharSequence cs) {
        if (cs != null) {
            int length = cs.length();
            for (int i = 0; i < length; i++) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @see #isBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * 判断对象是否不为空
     * @param object 对象
     */
    public static boolean checkValNotNull(Object object) {
        if (object instanceof CharSequence) {
            return isNotEmpty((CharSequence) object);
        }
        return object != null;
    }

    /**
     * 判断对象是否为空
     * @param object 对象
     */
    public static boolean checkValNull(Object object) {
        return !checkValNotNull(object);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }
}
