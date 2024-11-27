package com.example.mod4.service.impl;

import com.example.mod4.aop.AccessibleDeleteComment;
import com.example.mod4.aop.AccessibleUpdateComment;
import com.example.mod4.model.Comment;
import com.example.mod4.model.News;
import com.example.mod4.model.User;
import com.example.mod4.repository.CommentRepository;
import com.example.mod4.service.CommentService;
import com.example.mod4.service.NewsService;
import com.example.mod4.service.UserService;
import com.example.mod4.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final NewsService newsService;


    @Override
    public List<Comment> findAll() {
        log.debug("CommentServiceImpl -> findAll");
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        log.debug("CommentServiceImpl -> findById id= {}", id);
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Comment> findAllByUserId(Long userId) {
        log.debug("CommentServiceImpl -> findAllByUserId userId= {}", userId);
        List<Comment> comments = commentRepository.findAllByUserId(userId);
        if (comments != null) {
            return comments;
        }
        return new ArrayList<>();
    }

    @Override
    public List<Comment> findAllByNewsId(Long newsId) {
        log.debug("CommentServiceImpl -> findAllByNewsId newsId= {}", newsId);
        return commentRepository.findAllByNewsId(newsId);
    }

    @Override
    public Comment save(Comment comment) {
        log.debug("CommentServiceImpl -> save comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        user.addComment(comment);
        news.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    @AccessibleUpdateComment
    public Comment update(Comment comment) {
        log.debug("CommentServiceImpl -> update comment= {}", comment);
        User user = userService.findById(comment.getUser().getId());
        News news = newsService.findById(comment.getNews().getId());
        Comment existedComment = findById(comment.getId());

        BeanUtils.copyNonNullProperties(comment, existedComment);
        existedComment.setUser(user);
        existedComment.setNews(news);
        return commentRepository.save(existedComment);
    }

    @Override
    @Transactional
    @AccessibleDeleteComment
    public Comment deleteByIdAndUserId(Long id, Long userId) {
        log.debug("CommentServiceImpl -> deleteByIdAndUserId id= {}, userId= {}", id, userId);
        Comment deletedComment = commentRepository.findById(id).orElse(null);
        commentRepository.deleteByIdAndUserId(id, userId);
        return deletedComment;
    }

    @Override
    public Long countAllByNewsId(Long newsId) {
        log.debug("CommentServiceImpl -> countAllByNewsId newsId= {}", newsService);
        return commentRepository.countAllByNewsId(newsId);
    }
}