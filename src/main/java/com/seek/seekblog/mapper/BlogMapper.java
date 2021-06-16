package com.seek.seekblog.mapper;

import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.Tag;
import com.seek.seekblog.entity.Type;
import com.seek.seekblog.vo.BlogQuery;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {

    @Results({
            @Result(column="share_statement",property="shareStatement"),
            @Result(column="first_picture",property="firstPicture"),
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime"),
            @Result(column="type_id",property="typeId"),
            @Result(column="user_id",property="userId")
    })
    @Select("select * from t_blog")
    List<Blog> listAllBlogs();

    /*@Results({
            @Result(column="share_statement",property="shareStatement"),
            @Result(column="first_picture",property="firstPicture"),
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime"),
            @Result(column="type_id",property="typeId"),
            @Result(column="user_id",property="userId")
    })
    @Select({"<script>",
            "select distinct tb.* from t_blog tb inner join t_blog_tag tbt",
            "<where>",
            "<if test='title != null and title != \"\"'>",
            "and title like #{title}",
            "</if>",
            "<if test='typeId != null and typeId != \"\"'>",
            "and type_id=#{typeId}",
            "</if>",
            "<if test='recommend != null and recommend != \"\"'>",
            "and recommend=#{recommend}",
            "</if>",
            "<if test='tagId != null and tagId != \"\"'>",
            "and tb.id=tbt.blog_id and tbt.tag_id=#{tagId}",
            "</if>",
            "</where>",
            "</script>"})*/
    List<Blog> findAllBlogs(BlogQuery blogQuery);

    @Results({
            @Result(column="share_statement",property="shareStatement"),
            @Result(column="first_picture",property="firstPicture"),
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime"),
            @Result(column="type_id",property="typeId"),
            @Result(column="user_id",property="userId")
    })
    @Select("select * from t_blog where id=#{id}")
    Blog findById(Long id);

    @Select("select count(*) from t_blog")
    Long count();

    @Select("select * from t_blog where name=#{name}")
    List<Blog> listByName(String name);

    @Select("select * from t_blog where type_id=#{id}")
    List<Blog> listByType(Type type);

    @Results({
            @Result(column="share_statement",property="shareStatement"),
            @Result(column="first_picture",property="firstPicture"),
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime"),
            @Result(column="type_id",property="typeId"),
            @Result(column="user_id",property="userId")
    })
    @Select("select * from t_blog where recommend=#{b} order by update_time desc limit #{size}")
    List<Blog> listByRecommend(boolean b,Integer size);

    @Insert("insert into t_blog (title,content,introduce,first_picture,flag,views,appreciation,share_statement,commentabled,published,recommend,create_time,update_time,type_id,user_id)" +
            "values(#{title},#{content},#{introduce},#{firstPicture},#{flag},#{views},#{appreciation},#{shareStatement},#{commentabled},#{published},#{recommend},#{createTime},#{updateTime},#{typeId},#{userId})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    Long save(Blog blog);

    @Update("update t_blog set title=#{title},content=#{content},introduce=#{introduce},first_picture=#{firstPicture},flag=#{flag},appreciation=#{appreciation},share_statement=#{shareStatement}," +
            "commentabled=#{commentabled},published=#{published},recommend=#{recommend},update_time=#{updateTime},type_id=#{typeId} where id = #{id}")
    void update(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    void delete(Long id);

    @Select("select count(*) from t_blog where type_id=#{id}")
    Long countByTypeId(Long id);

    @Update("update t_blog set type_id=null where type_id=#{id}")
    void updateByTypeId(Long id);

    List<Blog> listByStrBlog(String str);

    @Update("update t_blog set views=views+1 where id=#{id}")
    void updateViews(Long id);

    @Select("select date_format(b.update_time,'%Y') as year from t_blog b group by year order by year desc")
    List<String> findGroupYear();

    @Results({
            @Result(column="share_statement",property="shareStatement"),
            @Result(column="first_picture",property="firstPicture"),
            @Result(column="create_time",property="createTime"),
            @Result(column="update_time",property="updateTime"),
            @Result(column="type_id",property="typeId"),
            @Result(column="user_id",property="userId")
    })
    @Select("select * from t_blog b where date_format(b.update_time,'%Y') = #{year}")
    List<Blog> findByYear(String year);

}
