package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * @description 留言板评论
 */
@Data
@Schema(description = "留言板评论")
public class MessageBoardCommentVo {
    @Schema(name = "comment", description = "评论")
    public CommentVo comment;

    @Schema(name = "replies", description = "回复")
    public List<CommentVo> replies;

    @Schema(name = "replyCount", description = "回复总条数")
    public Long replyCount;
}
