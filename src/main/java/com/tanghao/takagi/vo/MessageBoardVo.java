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
    @Schema(name = "reply", description = "回复")
    public ReplyVo reply;

    @Schema(name = "subReplies", description = "子回复")
    public List<ReplyVo> subReplies;

    @Schema(name = "subReplyCount", description = "回复总条数")
    public Long subReplyCount;
}
