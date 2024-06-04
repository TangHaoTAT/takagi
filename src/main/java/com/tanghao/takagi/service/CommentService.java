package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.Comment;
import com.tanghao.takagi.mapper.CommentMapper;
import com.tanghao.takagi.vo.CommonPage;
import com.tanghao.takagi.vo.MessageBoardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 评论Service
 */
@Slf4j
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> {
    /**
     * 按页获取留言板内容
     * @param currentNo 第几页
     * @param pageSize 每页显示条数
     */
    public CommonPage<List<MessageBoardVo>> getMessageBoardByPage(Long currentNo, Long pageSize) {
        Page<Comment> commentPage = getBaseMapper().getCommentByPage(new Page<>(currentNo, pageSize), "MESSAGE_BOARD", null);
        Long commentTotal = commentPage.getTotal();
        List<Comment> commentRecords = commentPage.getRecords();
        List<Long> commentIds = commentRecords.stream().map(Comment::getId).distinct().collect(Collectors.toList());
        List<Comment> replyRecords = getBaseMapper().list3RepliesByCommentIds(commentIds);


        return null;
    }
}
