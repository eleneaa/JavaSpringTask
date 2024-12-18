package com.example.mod4.aop;

import com.example.mod4.model.News;
import com.example.mod4.exception.AccessibleException;
import com.example.mod4.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class DeleteNewsAspect {
    private final NewsService newsService;

    @Around("@annotation(AccessibleDeleteNews)")
    public Object userControlDeleteNewsAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs(); // Получаем аргументы метода

        Long newsId = (Long) args[0];
        Long userId = (Long) args[1];
        News deletingNews = newsService.findById(newsId);

        // Проверяем, является ли пользователь владельцем поста
        if (deletingNews.getUser().getId() == userId) {
            return proceedingJoinPoint.proceed(args); // Если да, продолжаем выполнение метода
        }


        throw new AccessibleException("User is not allowed to delete this news");
    }
}
