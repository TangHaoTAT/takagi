package com.tanghao.takagi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

/**
 * @description Http请求工具类
 */
@Slf4j
@Component
public class HttpClientUtil {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * get请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 参数
     * @param responseType 返回值类型
     */
    public <T> T get(String url, Map<String, String> headers, Map<String, Object> params, Class<T> responseType) {
        if (null == url || url.trim().isEmpty()) {
            throw new RuntimeException("请求地址不能为空");
        }
        if (null == responseType) {
            throw new RuntimeException("返回值类型不能为空");
        }
        // 拼接参数
        StringBuffer sbUrl = new StringBuffer(url);
        if (!CollectionUtils.isEmpty(params)) {
            params.entrySet().forEach(param -> {
                sbUrl.append("&").append(param.getKey()).append("=").append(param.getValue());
            });
            url = sbUrl.toString().replaceFirst("&", "?");
        }
        // 创建请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            headers.entrySet().forEach(header -> {
                httpHeaders.add(header.getKey(), header.getValue());
            });
        }
        // 创建请求实体
        HttpEntity httpEntity = new HttpEntity<>(null, httpHeaders);
        // 发起请求
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
            log.info("{} url: {}, httpHeaders: {}, responseBody: {}", HttpMethod.GET, url, httpHeaders, responseEntity.getBody());
            return responseEntity.getBody();
        }catch (Exception e) {
            log.error("{} url: {}, httpHeaders: {}, errorMessage: {}", HttpMethod.GET, url, httpHeaders, e.getMessage());
        }
        return null;
    }

    /**
     * post请求
     * @param url 请求地址
     * @param headers 请求头
     * @param params 参数
     * @param responseType 返回值类型
     */
    public <T> T post(String url, Map<String, String> headers, Map<String, Object> params, Class<T> responseType) {
        if (null == url || url.trim().isEmpty()) {
            throw new RuntimeException("请求地址不能为空");
        }
        if (null == responseType) {
            throw new RuntimeException("返回值类型不能为空");
        }
        // 创建请求头
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            headers.entrySet().forEach(header -> {
                httpHeaders.add(header.getKey(), header.getValue());
            });
        }
        // 创建请求实体
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        // 发起请求
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
            log.info("{} url: {}, httpHeaders: {}, httpBody: {}, responseBody: {}", HttpMethod.POST, url, httpHeaders, params, responseEntity.getBody());
            return responseEntity.getBody();
        }catch (Exception e) {
            log.error("{} url: {}, httpHeaders: {}, httpBody: {}, errorMessage: {}", HttpMethod.POST, url, httpHeaders, params, e.getMessage());
        }
        return null;
    }

}