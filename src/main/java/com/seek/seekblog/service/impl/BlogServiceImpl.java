package com.seek.seekblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seek.seekblog.NotFoundException;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.Tag;
import com.seek.seekblog.entity.User;
import com.seek.seekblog.mapper.*;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.util.MarkdownUtils;
import com.seek.seekblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.Transient;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogTagMapper blogTagMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transient
    public Blog getBlog(Long id) {
        Blog blog = blogMapper.findById(id);
        blog.setType(typeMapper.findById(blog.getTypeId()));
        blog.setUser(userMapper.findById(blog.getUserId()));
        List<Long> list = blogTagMapper.findByBlogId(id);
        String ids = "";
        for (Long l:list ) {
            blog.getTags().add(tagMapper.findById(l));
            if (!ids.equals("")) {
                ids = ids + ",";
            }
            ids = ids + l;
        }
        blog.setTagIds(ids);
        return blog;
    }

    @Override
    @Transient
    public Long countByTypeId(Long id) {
        return blogMapper.countByTypeId(id);
    }

    @Override
    @Transient
    public Long countByTagId(Long id) {
        return blogTagMapper.countByTagId(id);
    }

    @Override
    @Transient
    public List<Blog> listByRecommend() {
        return blogMapper.listByRecommend(true,5);
    }

    @Override
    @Transient
    public PageInfo<Blog> listBlogs(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Blog> blogs = blogMapper.listAllBlogs();
        for (Blog b:blogs) {
            b.setType(typeMapper.findById(b.getTypeId()));
            User user = userMapper.findById(b.getUserId());
            user.setPassword("");
            b.setUser(user);
            List<Long> list = blogTagMapper.findByBlogId(b.getId());
            for (Long l:list) {
                b.getTags().add(tagMapper.findById(l));
            }
        }

        PageInfo<Blog> tagPageInfo = new PageInfo<>(blogs,pageSize);
        return tagPageInfo;
    }

    @Override
    @Transient
    public PageInfo<Blog> listBlogs(Integer pageNum, Integer pageSize, BlogQuery blogQuery) {
        PageHelper.startPage(pageNum,pageSize);
        blogQuery.setTitle(blogQuery.getTitle());
        List<Blog> blogs = blogMapper.findAllBlogs(blogQuery);
        for (Blog b:blogs) {
            b.setType(typeMapper.findById(b.getTypeId()));
            User user = userMapper.findById(b.getUserId());
            user.setPassword("");
            b.setUser(user);
            List<Long> list = blogTagMapper.findByBlogId(b.getId());
            for (Long l:list) {
                b.getTags().add(tagMapper.findById(l));
            }
        }

        PageInfo<Blog> tagPageInfo = new PageInfo<>(blogs,pageSize);
        return tagPageInfo;
    }

    @Override
    @Transient
    public Blog saveBlog(Blog blog, RedirectAttributes attributes) {

        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blogMapper.save(blog);
        typeMapper.addTypeNumber(blog.getTypeId());
        if (blog.getId()==null){
            attributes.addFlashAttribute("message","???????????????");
        }else {
            /*???blog_id???tag_id??????t_blog_tag??????*/
            for (Tag tag : blog.getTags()) {
                blogTagMapper.save(blog.getId(), tag.getId());
                tagMapper.addTagNumber(tag.getId());
            }
            attributes.addFlashAttribute("message","????????????");
        }
        return blog;
    }

    @Override
    @Transient
    public Blog updateBlog(Blog blog, RedirectAttributes attributes) {

        Blog b = blogMapper.findById(blog.getId());
        if (b == null){
            throw new NotFoundException("????????????");
        }

        blog.setViews(b.getViews());
        blog.setCreateTime(b.getCreateTime());
        blog.setUpdateTime(new Date());
        blog.setType(typeMapper.findById(b.getTypeId()));
        blog.setComments(b.getComments());


        attributes.addFlashAttribute("message", "???????????????");
        blogMapper.update(blog);

        blogTagMapper.deleteByBlogId(blog.getId());
        for (Tag tag:blog.getTags()) {
            blogTagMapper.save(blog.getId(),tag.getId());
        }

        return blog;
    }

    @Override
    @Transient
    public void deleteBlog(Long id,RedirectAttributes attributes) {
        attributes.addFlashAttribute("message","???????????????");
        /*??????blog????????????????????????blog???????????????*/
        blogTagMapper.deleteByBlogId(id);
        commentMapper.deleteByBlogId(id);
        /*??????blog?????????blog?????????type???tag????????????*/
        typeMapper.reduceTypeNumber(blogMapper.findById(id).getTypeId());
        List<Long> longs = blogTagMapper.findByBlogId(id);
        for(Long l : longs) {
            tagMapper.reduceTagNumber(l);
        }
        blogMapper.delete(id);
    }

    @Override
    @Transient
    public PageInfo<Blog> listByStrBlog(String str,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.listByStrBlog(str);

        for (Blog b:blogs) {
            b.setType(typeMapper.findById(b.getTypeId()));
            User user = userMapper.findById(b.getUserId());
            user.setPassword("");
            b.setUser(user);
            List<Long> list = blogTagMapper.findByBlogId(b.getId());
            for (Long l:list) {
                b.getTags().add(tagMapper.findById(l));
            }
        }

        PageInfo<Blog> tagPageInfo = new PageInfo<>(blogs,pageSize);
        return tagPageInfo;
    }

    @Override
    @Transient
    public Blog getBlogConvert(Long id) {
        Blog blog = getBlog(id);
        if (blog == null){
            throw new NotFoundException("???????????????");
        }
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()));
        blogMapper.updateViews(id);
        return blog;
    }

    @Override
    @Transient
    public Map<String,List<Blog>> archiveBlog(){
        List<String> years = blogMapper.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year,blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog(){
        return blogMapper.count();
    }

    /**
     * ?????????blog???tag??????????????????
     */
    private void addTag(List<Blog> blogs){
        for (Blog b:blogs) {
            b.setType(typeMapper.findById(b.getTypeId()));
            addUser(b);
            List<Long> list = blogTagMapper.findByBlogId(b.getId());
            for (Long l:list) {
                b.getTags().add(tagMapper.findById(l));
            }
        }
    }

    /**
     * ???blog???user??????????????????
     */
    private void addUser(Blog blog){
        User user = userMapper.findById(blog.getUserId());
        user.setPassword("");
        blog.setUser(user);
    }
}
