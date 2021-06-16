package com.seek.seekblog.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Blog;
import com.seek.seekblog.entity.User;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.TagService;
import com.seek.seekblog.service.TypeService;
import com.seek.seekblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /*进入blog列表页面*/
    @GetMapping("/blogs")
    public String blogs(HttpServletRequest request, Model model){
        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Blog> pageInfo = blogService.listBlogs(pageNo,10);
        BlogQuery blogQuery = new BlogQuery();
        model.addAttribute("page",pageInfo);
        //setTypeAndTag(model);
        model.addAttribute("blog",blogQuery);
        model.addAttribute("path","");
        return "admin/blogs";
    }

    /*通过条件查询blog 和上下页*/
    @PostMapping("/blogs/search")
    public String search(BlogQuery blogQuery,Integer pageNum, Model model){
        Integer pageNo = pageNum==null ? 1 : pageNum;/*request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));*/
//        String json = JSONObject.toJSONString(blogQuery);

        setTypeAndTag(model);
        model.addAttribute("page",blogService.listBlogs(pageNo,10,blogQuery));
//        model.addAttribute("blog",json);
        model.addAttribute("path","/search");
        return "admin/blogs :: blogList";
    }

    /*上下一页请求 */
    @GetMapping("/blogs/search")
    public String input(HttpServletRequest request, Model model){
        String blogQuery = request.getParameter("blogQuery");

        BlogQuery b = JSONObject.parseObject(blogQuery,BlogQuery.class);

        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Blog> pageInfo = blogService.listBlogs(pageNo,10,b);
        String json = JSONObject.toJSONString(b);

        setTypeAndTag(model);
        model.addAttribute("page",pageInfo);
        model.addAttribute("blog",json);
        model.addAttribute("path","/search");
        return "admin/blogs";
    }

    /*删除blog*/
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id,attributes);
        return "redirect:/admin/blogs";
    }

    /*进入新建blog页面*/
    @GetMapping("/blogs/input")
    public String input(Model model){
        Blog blog = new Blog();
        blog.setFlag("原创");
        blog.setAppreciation(true);
        blog.setCommentabled(true);
        blog.setRecommend(true);
        blog.setShareStatement(true);
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        return "admin/blogs-input";
    }

    /*新建blog  修改blog*/
    @PostMapping("/blogs")
    public String post(Blog blog,RedirectAttributes attributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());

        String ids = blog.getTagIds();
        if (!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for (String id:idarray) {
                blog.getTags().add(tagService.getTag(new Long(id)));
            }
        }

        if(blog.getId()!=null){
            blogService.updateBlog(blog,attributes);
        }else {
            blogService.saveBlog(blog,attributes);
        }

        return "redirect:/admin/blogs";
    }

    /*进入修改blog*/
    @GetMapping("/blogs/{id}/input")
    public String input(@PathVariable Long id,Model model){
        Blog blog = blogService.getBlog(id);
        blog.setType(typeService.getType(blog.getTypeId()));

        setTypeAndTag(model);
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }








    /*查询type和tag在html中显示*/
    private void setTypeAndTag(Model model){
        model.addAttribute("types",typeService.listTypes());
        model.addAttribute("tags",tagService.listTags());
    }
}
