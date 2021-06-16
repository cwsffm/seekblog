package com.seek.seekblog.service;

import com.seek.seekblog.entity.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listComment(Long id);

    Comment saveComment(Comment comment);
}
