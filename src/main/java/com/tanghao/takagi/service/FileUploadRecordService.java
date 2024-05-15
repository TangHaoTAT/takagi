package com.tanghao.takagi.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.FileUploadRecord;
import com.tanghao.takagi.mapper.FileUploadRecordMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 * @description 文件上传Service
 */
@Service
public class FileUploadRecordService extends ServiceImpl<FileUploadRecordMapper, FileUploadRecord> {

    /**
     * 插入文件上传记录
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param fileSize 文件大小
     * @param relativePath 相对路径
     * @param uploadDate 上传时间
     */
    @Transactional
    public FileUploadRecord insertFileUploadRecord(String fileName, String fileType, Long fileSize, String relativePath, Date uploadDate) {
        FileUploadRecord fileUploadRecord = new FileUploadRecord();
        fileUploadRecord.setName(fileName);
        fileUploadRecord.setType(fileType);
        fileUploadRecord.setSize(fileSize);
        fileUploadRecord.setRelativePath(relativePath);
        fileUploadRecord.setUserId(StpUtil.getLoginIdAsLong());
        fileUploadRecord.setUploadDate(uploadDate);
        this.save(fileUploadRecord);
        return fileUploadRecord;
    }
}
