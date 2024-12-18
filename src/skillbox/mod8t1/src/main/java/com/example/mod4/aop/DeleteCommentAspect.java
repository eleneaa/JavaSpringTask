package com.example.mod4.aop;

import com.example.mod4.model.Comment;
import com.example.mod4.exception.AccessibleException;
import com.example.mod4.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DeleteCommentAspect {

    private final CommentService commentService;

    @Around("@annotation(AccessibleDeleteComment)")
    public Object userControlDeleteCommentAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs(); // Получаем аргументы метода
        Long commentId = (Long) args[0];
        Long userId = (Long) args[1];

        Comment deletingComment = commentService.findById(commentId);

        // Проверяем, является ли пользователь владельцем комментария
        if (deletingComment.getUser().getId() == userId) {
            return proceedingJoinPoint.proceed(args);
        }

        throw new AccessibleException("User is not allowed to delete this comment");
    }
}
