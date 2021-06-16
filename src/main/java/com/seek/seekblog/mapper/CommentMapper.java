package com.seek.seekblog.mapper;

import com.seek.seekblog.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Results({
            @Result(column="create_time",property="createTime"),
            @Result(column="blog_id",property="blogId"),
            @Result(column="parent_comment_id",property="parentCommentId")
    })
    @Select("select * from t_comment")
    List<Comment> listAllComments();

    @Results({
            @Result(column="create_time",property="createTime"),
            @Result(column="blog_id",property="blogId"),
            @Result(column="parent_comment_id",property="parentCommentId")
    })
    @Select("select * from t_comment where blog_id=#{id}")
    List<Comment> listComment(Long id);

    @Insert("insert into t_comment (nickname,email,content,avatar,create_time,blog_id,parent_comment_id,admin)values(#{nickname},#{email},#{content},#{avatar},#{createTime},#{blogId},#{parentCommentId},#{admin})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long saveComment(Comment comment);

    @Delete("delete from t_comment where blog_id=#{id}")
    void deleteByBlogId(Long id);
}
