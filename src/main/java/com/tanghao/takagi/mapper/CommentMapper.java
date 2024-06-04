package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanghao.takagi.entity.Comment;
import com.tanghao.takagi.vo.CommentVo;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @description 评论Mapper
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 按页获取留言板内容
     * @param page new Page(当前页, 每页显示条数)
     * @param type 对应类型
     * @param typeId 对应类型id
     */
    @Results(id = "getMessageBoardContentByPage", value = {
            @Result(property = "commentId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "replies", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.CommentMapper.listRepliesByCommentId")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment ",
            " where 1=1 ",
            " and type = #{type} ",
            " <when test='typeId!=null'>",
                " and type_id = #{typeId} ",
            " </when>",
            " and root_comment_id is null ",
            " order by id asc",
            "</script>"})
    Page<CommentVo> getCommentVoByPage(Page<CommentVo> page, String type, Long typeId);

    /**
     * 根据评论id获取对应的回复
     * @param commentId 评论id
     */
    @Results(id = "listRepliesByCommentId", value = {
            @Result(property = "commentId", column = "id", id = true),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUser", column = "from_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUser", column = "to_user_id", one = @One(select = "com.tanghao.takagi.mapper.UserMapper.getUserInfoVoById")),
            @Result(property = "replies", column = "id", many = @Many(select = "com.tanghao.takagi.mapper.CommentMapper.listRepliesByCommentId")),
    })
    @Select({"<script>",
            " select id, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment ",
            " where 1=1 ",
            " and root_comment_id = #{commentId} ",
            " order by id asc ",
            "</script>"})
    List<CommentVo> listRepliesByCommentId(Long commentId);
}
