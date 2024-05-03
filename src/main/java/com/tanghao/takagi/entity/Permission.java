package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description 权限
 */
@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;// 权限id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String code;// 权限代码

    private String introduce;// 权限介绍

}
