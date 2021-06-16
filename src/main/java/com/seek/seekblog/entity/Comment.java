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
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar;

    private Date createTime;

    private Long blogId;
    private Long parentCommentId;
    private boolean admin;

    private Blog blog;
    private Comment parentComment;
    private List<Comment> replyComments = new ArrayList<>();

}
