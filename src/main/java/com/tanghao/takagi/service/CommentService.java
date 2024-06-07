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
        Page<MessageBoardVo> page = getBaseMapper().getMessageBoardVoByPage(new Page<>(currentNo, pageSize));
        return CommonPage.get(currentNo, pageSize, page.getTotal(), page.getRecords());
    }
}
