package com.example.mod4.repository;

import com.example.mod4.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByNewsId(Long newsId);

    Long countAllByNewsId(Long newsId);

    void deleteByIdAndUserId(Long id, Long userId);
}