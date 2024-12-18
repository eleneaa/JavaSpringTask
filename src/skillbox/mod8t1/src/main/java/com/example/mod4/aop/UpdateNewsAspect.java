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
public class UpdateNewsAspect {
    private final NewsService newsService;

    @Around("@annotation(AccessibleUpdateNews)")
    public Object userControlUpdateNewsAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Object[] args = proceedingJoinPoint.getArgs();

        News updateNews = (News) args[0];
        Long updateUserId = updateNews.getUser().getId();
        Long updateNewsId = updateNews.getId();
        News news = newsService.findById(updateNewsId);
        if (news.getUser().getId() == updateUserId) {
            return proceedingJoinPoint.proceed(args);
        }
        throw new AccessibleException("User is not allowed to update this news");
    }
}