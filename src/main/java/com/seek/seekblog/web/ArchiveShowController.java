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
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String tags( Model model){
        model.addAttribute("page",blogService.archiveBlog());
        model.addAttribute("number",blogService.countBlog());
        return "archives";
    }
}
