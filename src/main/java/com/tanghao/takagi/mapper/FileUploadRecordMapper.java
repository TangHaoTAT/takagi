package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tanghao.takagi.entity.FileUploadRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description 文件上传Mapper
 */
@Mapper
public interface FileUploadRecordMapper extends BaseMapper<FileUploadRecord> {
}
