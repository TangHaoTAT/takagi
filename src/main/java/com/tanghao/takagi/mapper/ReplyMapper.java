package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanghao.takagi.entity.Reply;
import com.tanghao.takagi.vo.ReplyVo;
import com.tanghao.takagi.vo.MessageBoardVo;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @description 回复Mapper
 */
@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {
    /**
     * 按页获取留言板回复
     * @param page new Page(当前页, 每页显示条数)
     */
    @Results(id = "getMessageBoardReplies", value = {
            @Result(property = "reply", column = "id", one = @One(select = "com.tanghao.takagi.mapper.ReplyMapper.getMessageBoardReplyByReplyId")),
            @Result(property = "subReplies", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.ReplyMapper.list3SubRepliesByReplyId")),
            @Result(property = "subReplyCount", column = "id", one = @One(select = "com.tanghao.takagi.mapper.ReplyMapper.countSubReplyCountByReplyId")),
    })
    @Select({"<script>",
            " select id from reply ",
            " where 1=1 ",
            " and root_reply_id is null ",
            " and type = 'MESSAGE_BOARD' ",
            " and deleted = false ",
            " order by id asc",
            "</script>"})
    Page<MessageBoardVo> getMessageBoardReplies(Page<Reply> page);

    /**
     * 根据回复id获取回复
     * @param replyId 回复id
     */
    @Results(id = "getMessageBoardReplyByReplyId", value = {
            @Result(property = "replyId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootReplyId", column = "root_reply_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_reply_id, to_user_id from reply ",
            " where 1=1 ",
            " and id = #{replyId} ",
            "</script>",})
    ReplyVo getMessageBoardReplyByReplyId(Long replyId);

    /**
     * 根据回复id获取前3条子回复
     * @param replyId 回复id
     */
    @Results(id = "list3SubRepliesByReplyId", value = {
            @Result(property = "replyId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootReplyId", column = "root_reply_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_reply_id, to_user_id from reply a ",
            " where 1=1 ",
            " and a.root_reply_id = #{replyId} ",
            " and a.deleted = false ",
            " and (select count(*) from reply b where b.root_reply_id = a.root_reply_id and b.id &lt;= a.id and b.deleted = false) &lt;= 3 ",
            " order by id asc ",
            "</script>"})
    List<ReplyVo> list3SubRepliesByReplyId(Long replyId);

    /**
     * 根据回复id统计子回复数量
     * @param replyId 回复id
     */
    @Select({"<script>",
            " select count(*) from reply ",
            " where 1=1 ",
            " and root_reply_id = #{replyId} ",
            " and deleted = false ",
            "</script>"})
    Long countSubReplyCountByReplyId(Long replyId);

    /**
     * 按页获取子回复
     * @param page new Page(当前页, 每页显示条数)
     * @param replyId 回复id
     */
    @Results(id = "getSubReplies", value = {
            @Result(property = "replyId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootReplyId", column = "root_reply_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoByUserId")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_reply_id, to_user_id from reply ",
            " where 1=1 ",
            " and root_reply_id = #{replyId} ",
            " and deleted = false ",
            " order by id asc",
            "</script>"})
    Page<ReplyVo> getSubReplies(Page<Reply> page, Long replyId);

}
