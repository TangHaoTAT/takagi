package com.tanghao.takagi.config;

import com.tanghao.takagi.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description XssHttpServletRequestWrapper
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    /**
     * post请求体
     */
    private byte[] body;

    /**
     * 是否是文件上传
     */
    private boolean fileUpload = false;

    /**
     * xss脚本正则
     */
    private final static Pattern[] scriptPatterns = {
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
    };

    /**
     * 构造函数-获取post请求体
     */
    public XssHttpServletRequestWrapper(HttpServletRequest httpservletrequest) throws IOException {
        super(httpservletrequest);
        String sessionStream = getBodyString(httpservletrequest);
        body = sessionStream.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 读取post请求体
     */
    private String getBodyString(HttpServletRequest httpservletrequest) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream ins = httpservletrequest.getInputStream();
        boolean isMultipartContent = JakartaServletFileUpload.isMultipartContent(httpservletrequest);
        StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
        boolean isMultipart = standardServletMultipartResolver.isMultipart(httpservletrequest);
        if (isMultipartContent || isMultipart) {
            fileUpload = true;
        }
        try (BufferedReader isr = new BufferedReader(new InputStreamReader(ins, StandardCharsets.UTF_8));) {
            String line = "";
            while ((line = isr.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        }
        return sb.toString();
    }

    @Override
    public String[] getParameterValues(String str) {
        String[] temp = super.getParameterValues(str);
        if (temp == null) {
            return null;
        }
        int i = temp.length;
        String[] last = new String[i];
        for (int j = 0; j < i; j++) {
            last[j] = cleanXSS(temp[j]);
        }
        return last;
    }

    @Override
    public String getParameter(String str) {
        String temp = super.getParameter(str);
        if (temp != null && temp.length() > 0) {
            return cleanXSS(temp);
        }
        return null;
    }

    /**
     * 过滤请求体json格式的
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        String temp = new String(body);
        // 非文件上传进行过滤
        if (!fileUpload) {
            // 获取body中的请求参数
            Map<String, String> map = JacksonUtil.convertJsonToMap(temp, String.class, String.class);
            // 校验并过滤xss攻击和sql注入
            for (String key : map.keySet()) {
                map.put(key, cleanXSS(map.get(key)));
            }
            temp = JacksonUtil.convertObjectToJson(map);
        }
        // 将请求体参数流转
        final ByteArrayInputStream bais = new ByteArrayInputStream(temp.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    /**
     * 清除XSS
     */
    public String cleanXSS(String str) {
        String temp = str;
        // 校验xss脚本
        for (Pattern pattern : scriptPatterns) {
            temp = pattern.matcher(temp).replaceAll("");
        }
        return temp;
    }
}