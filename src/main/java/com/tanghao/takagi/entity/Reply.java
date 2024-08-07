package com.tanghao.takagi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * @description 回复
 */
@Data
@TableName("reply")
public class Reply {
    @TableId(type = IdType.AUTO)
    private Long id;// 回复id

    private Boolean deleted;// 逻辑删除:0=未删除,1=已删除

    private String content;// 内容

    private Date createDate;// 创建日期

    private Long fromUserId;// 用户id

    private Long typeId;// 对应类型id

    private String type;// 评论类型

    private Long likes;// 点赞次数

    private Long rootReplyId;// 根回复id

    private Long toUserId;// 被回复用户id
}
