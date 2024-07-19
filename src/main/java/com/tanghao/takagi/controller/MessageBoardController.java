package com.tanghao.takagi.controller;

import com.tanghao.takagi.service.ReplyService;
import com.tanghao.takagi.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @description 留言板Controller
 */
@Slf4j
@RestController
@RequestMapping("/messageBoard")
@Tag(name = "留言板")
public class MessageBoardController {
    @Autowired
    private ReplyService replyService;

    @GetMapping("/getMessageBoardReplies")
    @Operation(summary ="按页获取留言板")
    public CommonResult<CommonPage<List<MessageBoardVo>>> getMessageBoardReplies(@RequestParam(name = "pageNum", defaultValue = "1") Long pageNum,
                                                                                @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return CommonResult.data(replyService.getMessageBoardReplies(pageNum, pageSize));
    }

    @GetMapping("/getSubReplies")
    @Operation(summary ="按页获取子回复")
    public CommonResult<CommonPage<List<ReplyVo>>> getSubReplies(@RequestParam(name = "pageNum", defaultValue = "1") Long pageNum,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize,
                                                                 @RequestParam(name = "replyId") Long replyId) {
        return CommonResult.data(replyService.getSubReplies(pageNum, pageSize, replyId));
    }

    @PostMapping("/createReply")
    @Operation(summary = "发布回复")
    public CommonResult createReply(@RequestBody ReplyEditVo replyEditVo) {
        String content = replyEditVo.getContent();
        Long typeId = replyEditVo.getTypeId();
        String type = replyEditVo.getType();
        Long rootReplyId = replyEditVo.getRootReplyId();
        Long toUserId = replyEditVo.getToUserId();
        replyService.createReply(content, typeId, type, rootReplyId, toUserId);
        return CommonResult.ok();
    }
}
