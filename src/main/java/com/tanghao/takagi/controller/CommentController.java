package com.tanghao.takagi.controller;

import com.tanghao.takagi.service.CommentService;
import com.tanghao.takagi.vo.CommentVo;
import com.tanghao.takagi.vo.CommonPage;
import com.tanghao.takagi.vo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @description 评论Controller
 */
@Slf4j
@RestController
@RequestMapping("/comment")
@Tag(name = "评论")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getMessageBoardContentByPage")
    @Operation(summary ="按页获取留言板内容")
    public CommonResult<CommonPage<List<CommentVo>>> getMessageBoardContentByPage(@RequestParam(name = "currentNo", defaultValue = "1") Long currentNo,
                                                                                  @RequestParam(name = "pageSize", defaultValue = "10") Long pageSize) {
        return CommonResult.data(commentService.getMessageBoardContentByPage(currentNo, pageSize));
    }
}
