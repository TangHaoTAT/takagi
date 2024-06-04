package com.tanghao.takagi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tanghao.takagi.entity.Comment;
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
     * @param type 对应类型
     * @param typeId 对应类型id
     */
    @Results(id = "getCommentByPage", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUserId", column = "from_user_id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUserId", column = "to_user_id"),
    })
    @Select({"<script>",
            " select id, deleted, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment ",
            " where 1=1 ",
            " and root_comment_id is null ",
            " and type = #{type} ",
            " <when test='typeId!=null'>",
                " and type_id = #{typeId} ",
            " </when>",
            " order by id asc",
            "</script>"})
    Page<Comment> getCommentByPage(Page<Comment> page, String type, Long typeId);

    /**
     * 获取每条评论的前3条回复
     * @param commentIds 评论id列表
     */
    @Results(id = "list3RepliesByCommentIds", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "deleted", column = "deleted"),
            @Result(property = "content", column = "content"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "fromUserId", column = "from_user_id"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "likes", column = "likes"),
            @Result(property = "rootCommentId", column = "root_comment_id"),
            @Result(property = "toUserId", column = "to_user_id"),
    })
    @Select({"<script>",
            " select id, deleted, content, create_date, from_user_id, type_id, type, likes, root_comment_id, to_user_id from comment a ",
            " where 1=1 ",
            " and root_comment_id in ",
                "<foreach collection='commentIds' item='id' open='(' separator=',' close=')'>",
                "#{id}",
                "</foreach>",
            " and (select count(*) from comment b where b.root_comment_id = a.root_comment_id and b.id &lt;= a.id) &lt;= 3 ",
            " order by id asc ",
            "</script>"})
    List<Comment> list3RepliesByCommentIds(List<Long> commentIds);

}
