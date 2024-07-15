package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

/**
 * @description 留言板评论
 */
@Data
@Schema(description = "留言板评论")
public class MessageBoardVo {
    @Schema(name = "comment", description = "评论")
    public CommentVo comment;

    @Schema(name = "replies", description = "回复")
    public List<CommentVo> replies;

    @Schema(name = "replyNum", description = "当前回复页数")
    public Long replyNum = 1L;

    @Schema(name = "replySize", description = "每页回复数量")
    public Long replySize = 3L;

    @Schema(name = "replyCount", description = "回复总条数")
    public Long replyCount;
}
