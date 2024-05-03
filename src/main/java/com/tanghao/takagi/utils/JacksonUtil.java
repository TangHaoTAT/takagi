package com.tanghao.takagi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description Jackson工具类
 */
@Slf4j
public class JacksonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();// 对象映射器

    /**
     * 将对象转换成JSON字符串
     * @param object 对象
     */
    public static String convertObjectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换成对象
     * @param json JSON字符串
     * @param objectType 对象类型
     */
    public static <T> T convertJsonToObject(String json, Class<T> objectType) {
        try {
            return objectMapper.readValue(json, objectType);
        } catch (JsonProcessingException e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换成List列表
     * @param json JSON字符串
     * @param objectType 对象类型
     */
    public static <T> List<T> convertJsonToList(String json, Class<T> objectType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, objectType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Set集合
     * @param json JSON字符串
     * @param elementType 元素类型
     */
    public static <E> Set<E> convertJsonToSet(String json, Class<E> elementType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(Set.class, elementType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 将JSON字符串转换成Map集合
     * @param json JSON字符串
     * @param keyType 键类型
     * @param valueType 值类型
     */
    public static <K, V> Map<K, V> convertJsonToMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructMapType(Map.class, keyType, valueType);
            return objectMapper.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            log.error("exception message", e);
        }
        return null;
    }

    /**
     * 通过输入流获取对象
     * @param inputStream 输入流
     * @param objectType 对象类型
     */
    public static <T> T getObjectByInputStream(InputStream inputStream, Class<T> objectType) {
        try {
            return objectMapper.readValue(inputStream, objectType);
        } catch (IOException e) {
            log.error("exception message", e);
        }
        return null;
    }

}
