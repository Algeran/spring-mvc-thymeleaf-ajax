package com.springfreamwork.thymeleafajax.domain.services;


import com.springfreamwork.thymeleafajax.app.utility.NotFoundException;
import com.springfreamwork.thymeleafajax.domain.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();

    long countComments();

    Comment getCommentById(String id) throws NotFoundException;

    void updateComment(Comment comment) throws NotFoundException;

    void createComment(Comment comment);

    void deleteComment(String id);
}
