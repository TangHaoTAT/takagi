package com.tanghao.takagi.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;

/**
 * @description 回复
 */
@Data
@Schema(description = "回复")
public class ReplyVo {
    @Schema(name = "replyId", description = "回复id")
    public Long replyId;

    @Schema(name = "content", description = "内容")
    public String content;

    @Schema(name = "createDate", description = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date createDate;

    @Schema(name = "fromUser", description = "用户")
    public UserInfoVo fromUser;

    @Schema(name = "typeId", description = "对应类型id")
    public Long typeId;

    @Schema(name = "type", description = "评论类型")
    public String type;

    @Schema(name = "likes", description = "点赞次数")
    public Long likes;

    @Schema(name = "rootReplyId", description = "根回复id")
    public Long rootReplyId;

    @Schema(name = "toUser", description = "被回复用户")
    public UserInfoVo toUser;
}
