package com.seek.seekblog.service.impl;

import com.seek.seekblog.entity.Comment;
import com.seek.seekblog.mapper.CommentMapper;
import com.seek.seekblog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listComment(Long blogId) {
        List<Comment> commentList = commentMapper.listComment(blogId);
        /*把有parent的comment拿到*/
        for (Comment c: commentList) {
            if (c.getParentCommentId()!=null){
                /*放到parent中的replyComment集合中,将parent放到parentComment中*/
                for (Comment cl : commentList){
                    if (cl.getId()==c.getParentCommentId()){
                        cl.getReplyComments().add(c);
                        Comment newComment = new Comment();
                        /*将前一个对象拷贝给后一个对象*/
                        BeanUtils.copyProperties(cl,newComment);
                        newComment.setReplyComments(null);
                        c.setParentComment(newComment);
                    }
                }
            }
        }
        /*将集合中有parent的移除*/
        Iterator<Comment> it =commentList.iterator();
        while (it.hasNext())
        {
            Comment comment = it.next();
            if (comment.getParentCommentId()!=null){
                it.remove();
            }
        }
        /*将所有子节点放到根节点上的replyComment集合中*/
        for (Comment c : commentList) {
            for (Comment cl : c.getReplyComments())
                recursively(cl);
            c.getReplyComments().addAll(comments);
            comments.clear();
        }
        return commentList;
    }

    @Override
    public Comment saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        if (comment.getParentCommentId()==-1)
            comment.setParentCommentId(null);
        comment.setId(commentMapper.saveComment(comment));
        return comment;
    }

    List<Comment> comments = new ArrayList<>();
    private void recursively(Comment comment){
        if (comment.getReplyComments()!=null){
            for (Comment c : comment.getReplyComments()) {
                comments.add(c);
                recursively(c);
            }
        }

    }
}
