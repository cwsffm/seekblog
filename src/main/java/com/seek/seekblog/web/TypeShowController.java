package com.seek.seekblog.web;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.Type;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.TypeService;
import com.seek.seekblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{typeId}")
    public String types(@PathVariable Long typeId, HttpServletRequest request, Model model){
        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        List<Type> types = typeService.listTypes();

        if(typeId == -1)
            typeId = types.get(0).getId();
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(typeId);
        PageInfo<Blog> pageInfo = blogService.listBlogs(pageNo,10,blogQuery);
        model.addAttribute("types",types);
        model.addAttribute("page",pageInfo);
        model.addAttribute("typeId",typeId);
        return "types";
    }
}
