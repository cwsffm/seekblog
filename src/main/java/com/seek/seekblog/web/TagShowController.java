package com.seek.seekblog.web;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.Tag;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.TagService;
import com.seek.seekblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{tagId}")
    public String tags(@PathVariable Long tagId, HttpServletRequest request, Model model){
        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        List<Tag> tags = tagService.listTags();

        if(tagId == -1)
            tagId = tags.get(0).getId();

        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTagId(tagId);
        PageInfo<Blog> pageInfo = blogService.listBlogs(pageNo,10,blogQuery);
        model.addAttribute("tags",tags);
        model.addAttribute("page",pageInfo);
        model.addAttribute("tagId",tagId);
        return "tags";
    }
}
