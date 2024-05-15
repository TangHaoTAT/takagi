package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * @description 文件上传
 */
@Data
@TableName("file_upload_record")
public class FileUploadRecord {
    @TableId(type = IdType.AUTO)
    private Long id;// 文件上传id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String name;// 文件名

    private String type;// 文件类型

    private Long size;// 文件大小

    private String relativePath;// 相对路径

    private Long userId;// 用户id

    private Date uploadDate;// 上传时间

}
