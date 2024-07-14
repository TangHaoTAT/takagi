package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanghao.takagi.entity.Comment;
import com.tanghao.takagi.vo.CommentVo;
import com.tanghao.takagi.vo.MessageBoardCommentVo;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @description 评论Mapper
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 按页获取留言板评论
     * @param page new Page(当前页, 每页显示条数)
     */
    @Results(id = "getCommentByPage", value = {
            @Result(property = "comment", column = "id", one = @One(select = "com.tanghao.takagi.mapper.CommentMapper.getMessageBoardCommentVoByCommentId")),
            @Result(property = "replies", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.CommentMapper.listMessageBoard3RepliesVoByCommentId")),
            @Result(property = "replyCount", column = "id", one = @One(select = "com.tanghao.takagi.mapper.CommentMapper.countReplyCountByCommentId")),
    })
    @Select({"<script>",
            " select id from comment ",
            " where 1=1 ",
            " and root_comment_id is null ",
            " and type = 'MESSAGE_BOARD' ",
            " and deleted = false ",
            " order by id asc",
            "</script>"})
    Page<MessageBoardCommentVo> getMessageBoardVoByPage(Page<Comment> page);

    /**
     * 根据评论id获取对应评论
     * @param commentId 评论id
     */
    @Results(id = "getMessageBoardCommentVoByCommentId", value = {
            @Result(property = "commentId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment ",
            " where 1=1 ",
            " and id = #{commentId} ",
            "</script>",})
    CommentVo getMessageBoardCommentVoByCommentId(Long commentId);

    /**
     * 根据评论id获取每条评论的前3条回复
     * @param commentId 评论id
     */
    @Results(id = "list3RepliesByCommentIds", value = {
            @Result(property = "commentId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment a ",
            " where 1=1 ",
//            " and root_comment_id in ",
//                "<foreach collection='commentIds' item='id' open='(' separator=',' close=')'>",
//                "#{id}",
//                "</foreach>",
            " and a.root_comment_id = #{commentId} ",
            " and a.deleted = false ",
            " and (select count(*) from comment b where b.root_comment_id = a.root_comment_id and b.id &lt;= a.id and b.deleted = false) &lt;= 3 ",
            " order by id asc ",
            "</script>"})
    List<CommentVo> listMessageBoard3RepliesVoByCommentId(Long commentId);

    /**
     * 根据评论id统计回复总数
     * @param commentId 评论id
     */
    @Select({"<script>",
            " select count(*) from comment ",
            " where 1=1 ",
            " and root_comment_id = #{commentId} ",
            " and deleted = false ",
            "</script>"})
    Long countReplyCountByCommentId(Long commentId);

    /**
     * 按页获取某评论的回复
     * @param page new Page(当前页, 每页显示条数)
     * @param commentId 评论id
     */
    @Results(id = "getRepliesByCommentIdByPage", value = {
            @Result(property = "commentId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment ",
            " where 1=1 ",
            " and root_comment_id = #{commentId} ",
            " and deleted = false ",
            " order by id asc",
            "</script>"})
    Page<CommentVo> getRepliesByCommentIdByPage(Page<Comment> page, Long commentId);

}
