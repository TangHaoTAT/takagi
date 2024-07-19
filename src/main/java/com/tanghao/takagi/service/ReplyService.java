package com.tanghao.takagi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanghao.takagi.entity.Reply;
import com.tanghao.takagi.mapper.ReplyMapper;
import com.tanghao.takagi.vo.ReplyVo;
import com.tanghao.takagi.vo.CommonPage;
import com.tanghao.takagi.vo.MessageBoardVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @description 回复Service
 */
@Slf4j
@Service
public class ReplyService extends ServiceImpl<ReplyMapper, Reply> {
    /**
     * 按页获取留言板回复
     * @param pageNum 第几页
     * @param pageSize 每页显示条数
     */
    public CommonPage<List<MessageBoardVo>> getMessageBoardReplies(Long pageNum, Long pageSize) {
        Page<MessageBoardVo> page = getBaseMapper().getMessageBoardReplies(new Page<>(pageNum, pageSize));
        return CommonPage.get(pageNum, pageSize, page.getTotal(), page.getRecords());
    }

    /**
     * 按页获取子回复
     * @param pageNum 第几页
     * @param pageSize 每页显示条数
     * @param replyId 回复id
     */
    public CommonPage<List<ReplyVo>> getSubReplies(Long pageNum, Long pageSize, Long replyId) {
        Page<ReplyVo> page = getBaseMapper().getSubReplies(new Page<>(pageNum, pageSize), replyId);
        return CommonPage.get(pageNum, pageSize, page.getTotal(), page.getRecords());
    }
}
