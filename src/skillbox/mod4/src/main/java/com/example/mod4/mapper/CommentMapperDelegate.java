package com.example.mod4.mapper;

import com.example.mod4.model.Comment;
import com.example.mod4.service.NewsService;
import com.example.mod4.service.UserService;
import com.example.mod4.web.request.UpsertCommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommentMapperDelegate implements CommentMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private NewsService newsService;

    @Override
    public Comment requestToComment(UpsertCommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setUser(userService.findById(request.getUserId()));
        comment.setNews(newsService.findById(request.getNewsId()));
        return comment;
    }

    @Override
    public Comment requestToComment(Long commentId, UpsertCommentRequest request) {
        Comment comment = requestToComment(request);
        comment.setId(commentId);
        return comment;
    }
}