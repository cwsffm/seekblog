package com.seek.seekblog.service;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.vo.BlogQuery;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    PageInfo<Blog> listBlogs(Integer pageNum,Integer pageSize);

    PageInfo<Blog> listBlogs(Integer pageNum, Integer pageSize, BlogQuery blogQuery);

    Blog saveBlog(Blog blog, RedirectAttributes attributes);

    Blog updateBlog(Blog blog, RedirectAttributes attributes);

    Long countByTypeId(Long id);

    Long countByTagId(Long id);

    List<Blog> listByRecommend();

    void deleteBlog(Long id,RedirectAttributes attributes);

    PageInfo<Blog> listByStrBlog(String str,Integer pageNum,Integer pageSize);

    Blog getBlogConvert(Long id);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
