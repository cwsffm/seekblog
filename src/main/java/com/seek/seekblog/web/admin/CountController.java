package com.seek.seekblog.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.seek.seekblog.entity.Count;
import com.seek.seekblog.service.BlogService;
import com.seek.seekblog.service.CountService;
import com.seek.seekblog.service.TagService;
import com.seek.seekblog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CountController {

    @Autowired
    private CountService countService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;

    @GetMapping("/lastSeven")
    public @ResponseBody String listLastSeven(){
        List<Count> lastSeven = countService.findLastSeven();

        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");

        String date[] = new String[7];
        Integer number[] = new Integer[7];

        for (int i=0;i<lastSeven.size()-1;i++){
            date[6-i] = sdf.format(lastSeven.get(i).getDate());
            number[6-i] = lastSeven.get(i).getBlogNumber()-lastSeven.get(i+1).getBlogNumber();
        }

        List list = new ArrayList();
        list.add(date);
        list.add(number);

        String json = JSONObject.toJSONString(list);
        return json;
    }

    @GetMapping("/listType")
    public @ResponseBody String listType(){
        return JSONObject.toJSONString(typeService.listTypes());
    }

    @GetMapping("/blogNumber")
    public @ResponseBody Long getBlogNumber(){
        return blogService.countBlog();
    }

    @GetMapping("/typeNumber")
    public @ResponseBody Long getTypeNumber(){
        return typeService.countType();
    }

    @GetMapping("/tagNumber")
    public @ResponseBody Long getTagNumber(){
        return tagService.countTag();
    }
}
