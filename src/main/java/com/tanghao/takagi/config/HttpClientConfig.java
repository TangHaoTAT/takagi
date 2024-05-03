package com.tanghao.takagi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @description HttpClient配置
 */
@Configuration
public class HttpClientConfig {
    /**
     * 配置Http连接工厂
     */
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(5000);
        simpleClientHttpRequestFactory.setReadTimeout(5000);
        return simpleClientHttpRequestFactory;
    }

    /**
     * 配置RestTemplate
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory){
        return new RestTemplate(factory);
    }

}