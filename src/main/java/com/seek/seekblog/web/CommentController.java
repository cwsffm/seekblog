package com.seek.seekblog.web;

import com.seek.seekblog.entity.Comment;
import com.seek.seekblog.entity.User;
import com.seek.seekblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listComment(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdmin(true);
        }else {
            comment.setAvatar("https://unsplash.it/100/100?image=1005");
        }
        Comment c = commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlogId();
    }

    @PostMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listComment(blogId));
        return "blog :: commentList";
    }
}
