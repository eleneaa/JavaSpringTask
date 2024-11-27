package com.example.mod4.service;

import com.example.mod4.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findAll();

    Comment findById(Long id);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByNewsId(Long newsId);

    Comment save(Comment comment);

    Comment update(Comment comment);

    Long countAllByNewsId(Long newsId);

    Comment deleteByIdAndUserId(Long id, Long userId);

}