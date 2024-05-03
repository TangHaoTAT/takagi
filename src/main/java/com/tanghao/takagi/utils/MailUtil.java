package com.tanghao.takagi.utils;

import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

/**
 * @description 邮件工具类
 */
@Slf4j
@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String from;// 发件人邮箱地址

    @Resource
    private JavaMailSender mailSender;// Java邮件发送者

    /**
     * 发送简单邮件消息
     * @param to 收件人邮箱地址
     * @param subject 主题
     * @param text 正文
     */
    public void sendSimpleMailMessage(String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * 发送邮件消息
     * @param to 收件人邮箱地址
     * @param subject 主题
     * @param text 正文
     * @param isHtmlText 正文部分是否为html
     * @param filePath 文件路径
     */
    public void sendMailMessage(String[] to, String subject, String text, Boolean isHtmlText, String[] filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(this.from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text, isHtmlText);
            if (null != filePath) {
                for (String path : filePath) {
                    if (null != path && !path.trim().isEmpty()) {
                        FileSystemResource fileItem = new FileSystemResource(path);
                        String fileName = fileItem.getFilename();
                        messageHelper.addAttachment(MimeUtility.encodeWord(fileName, "UTF-8", "B"), fileItem);
                    }
                }
            }
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("exception message", e);
        }
    }
}
