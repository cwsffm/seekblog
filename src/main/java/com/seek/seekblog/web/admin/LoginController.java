package com.seek.seekblog.web.admin;

import com.seek.seekblog.entity.User;
import com.seek.seekblog.service.UserService;
import com.seek.seekblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        Model model){
        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {
            attributes.addFlashAttribute("message","username or password error");
            return "redirect:/admin";
        }
    }

    @GetMapping("/login")
    public String home(HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null){
            return "admin/index";
        }else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        System.out.println("---logout---");
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
