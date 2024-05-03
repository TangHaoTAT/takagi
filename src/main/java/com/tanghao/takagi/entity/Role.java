package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @description 角色
 */
@Data
@TableName("role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;// 角色id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String code;// 角色代码

    private String introduce;// 角色介绍

}

