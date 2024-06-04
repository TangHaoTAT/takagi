package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * @description 留言板
 */
@Data
@Schema(description = "留言板")
public class MessageBoardVo {
    @Schema(name = "comment", description = "评论")
    public MessageBoardCommentVo comment;

    @Schema(name = "replies", description = "回复")
    public List<MessageBoardCommentVo> replies;

    @Schema(name = "replyTotal", description = "回复总数")
    public Long replyTotal;
}
