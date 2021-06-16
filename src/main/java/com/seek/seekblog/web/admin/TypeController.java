package com.seek.seekblog.web.admin;

import com.github.pagehelper.PageInfo;
import com.seek.seekblog.entity.Type;
import com.seek.seekblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(HttpServletRequest request, Model model){
        Integer pageNo = request.getParameter("pageNo") == null?1:Integer.valueOf(request.getParameter("pageNo"));
        PageInfo<Type> pageInfo = typeService.listTypes(pageNo,10);
        model.addAttribute("page",pageInfo);
        return "admin/types";
    }

    @GetMapping("/getTypes")
    public String getTypes(Model model){
        model.addAttribute("types",typeService.listTypes());
        return "admin/blogs :: typeList";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String save(Type type, RedirectAttributes attributes){
        Type t = typeService.saveType(type,attributes);
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String save(Type type,@PathVariable Long id, RedirectAttributes attributes){
        type.setId(id);
        Type t = typeService.updateType(type,attributes);
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id,attributes);
        return "redirect:/admin/types";
    }


}
