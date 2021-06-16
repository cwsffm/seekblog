package com.seek.seekblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Blog {


    private Long id;
    private String title;
    private String content;
    private String introduce;           //介绍
    private String firstPicture;        //首图
    private String flag;                //标记(原创、转载、翻译)
    private Integer views;              //浏览次数
    private boolean appreciation;       //赞赏开启
    private boolean shareStatement;     //版权开启
    private boolean commentabled;       //评论
    private boolean published;          //发布
    private boolean recommend;          //推荐
    private Date createTime;            //发布时间
    private Date updateTime;            //更新时间

    private Long typeId;
    private Long userId;
    private String tagIds;

    private Type type;
    private List<Tag> tags = new ArrayList<>();
    private User user;
    private List<Comment> comments = new ArrayList<>();
}
