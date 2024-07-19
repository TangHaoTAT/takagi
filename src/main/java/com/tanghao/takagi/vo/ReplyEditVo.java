package com.tanghao.takagi.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @description 回复编辑
 */
@Data
@Schema(description = "回复编辑")
public class ReplyEditVo {
    @NotBlank
    @Schema(name = "content", description = "内容")
    public String content;

    @Schema(name = "typeId", description = "对应类型id")
    public Long typeId;

    @Schema(name = "type", description = "评论类型")
    public String type;

    @Schema(name = "rootReplyId", description = "根回复id")
    public Long rootReplyId;

    @Schema(name = "toUserId", description = "被回复用户id")
    public Long toUserId;

}
