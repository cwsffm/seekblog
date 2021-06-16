package com.seek.seekblog.web;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.Tag;
import com.seek.seekblog.entity.Type;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.TagService;
import com.seek.seekblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping
    public String index(HttpServletRequest request, Model model){
        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Blog> pageInfo = blogService.listBlogs(pageNo,10);
        List<Type> listTypes = typeService.listTypes();
        for (Type t:listTypes) {
            t.setNumber(blogService.countByTypeId(t.getId()));
        }
        List<Tag> listTags = tagService.listTags();
        for (Tag t:listTags) {
            t.setNumber(blogService.countByTagId(t.getId()));
        }

        System.out.println(listTypes);
        System.out.println(listTags);
        model.addAttribute("page",pageInfo);
        model.addAttribute("types",listTypes);
        model.addAttribute("tags",listTags);
        model.addAttribute("recommendBlogs",blogService.listByRecommend());
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, Model model){

        PageInfo<Blog> pageInfo = blogService.listByStrBlog(query,1,10);
        model.addAttribute("query",query);
        model.addAttribute("page",pageInfo);
        return "search";
    }

    @GetMapping("/search")
    public String search(@RequestParam String query,@RequestParam Integer pageNo, Model model){

        PageInfo<Blog> pageInfo = blogService.listByStrBlog(query,pageNo==null?1:pageNo,10);
        model.addAttribute("page",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        model.addAttribute("blog",blogService.getBlogConvert(id));
        return "blog";
    }

    @GetMapping("/tags")
    public String tags(){
        return "tags";
    }
    /*@GetMapping("/types")
    public String types(){
        return "types";
    }*/


}
