package com.example.mod4.aop;

import com.example.mod4.model.Comment;
import com.example.mod4.model.News;
import com.example.mod4.model.RoleType;
import com.example.mod4.service.CommentService;
import com.example.mod4.service.NewsService;
import com.example.mod4.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class CheckOwnershipAspect {

    private final UserService userService;
    private final NewsService newsService;
    private final CommentService commentService;

    @Around("@annotation(CheckOwnership)")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String methodName = proceedingJoinPoint.getSignature().getName();

        Object[] args = proceedingJoinPoint.getArgs();
        UserDetails userDetails = getUserDetailsFromArgs(args);

        if (userDetails == null) {
            log.warn("UserDetails not found in method: {}", methodName);
            throw new AccessDeniedException("Access Denied: User is not authenticated");
        }

        log.info("Method called by user: {}. Role is: {}", userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")));

        log.info("Start method: {}", methodName);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String requestPath = request.getRequestURI();
            log.info("Request path: {}", requestPath);

            Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            String targetIdStr = Optional.ofNullable(pathVariables.get("id"))
                    .orElseThrow(() -> new IllegalArgumentException("ID parameter is missing in the request"));

            Long targetId = Long.valueOf(targetIdStr);
            log.info("Target ID: {}", targetId);

            List<String> authorities = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            if (!((requestPath.contains("news") || (requestPath.contains("comment")) && methodName.equals("update")))) {
                if (authorities.contains(RoleType.ROLE_ADMIN.name()) || authorities.contains(RoleType.ROLE_MODERATOR.name())) {
                    log.info("Method execution is allowed for {} (Administrator or Moderator)", authorities);
                    Object result = proceedingJoinPoint.proceed();
                    log.info("Method execution completed: {}", methodName);
                    return result;
                }
            }


            if (requestPath.contains("/users") && authorities.contains(RoleType.ROLE_USER.name())) {
                log.info("Calling userService to process request");
                Long userId = userService.findByUsername(userDetails.getUsername()).getId();

                if (targetId.equals(userId)) {
                    log.info("User {} successfully passed the access check for users {}", userId, targetId);
                    Object result = proceedingJoinPoint.proceed();
                    log.info("Method execution completed: {}", methodName);
                    return result;
                } else {
                    log.warn("User {} failed the access check for users {}", userId, targetId);
                    throw new AccessDeniedException("You did not pass the user access check.");
                }
            } else if (requestPath.contains("/news")) {
                log.info("Calling newsService to process request");
                News news = newsService.findById(targetId);
                Long newsUserId = news.getUser().getId();
                Long userId = userService.findByUsername(userDetails.getUsername()).getId();

                if (newsUserId.equals(userId)) {
                    log.info("User {} successfully passed the ownership check for news {}", userId, targetId);
                    Object result = proceedingJoinPoint.proceed();
                    log.info("Method execution completed: {}", methodName);
                    return result;
                } else {
                    log.warn("User {} failed the ownership check for news {}", userId, targetId);
                    throw new AccessDeniedException("You are not the owner of this news.");
                }
            } else if (requestPath.contains("/comment")) {
                log.info("Calling commentService to process request");
                Comment comment = commentService.findById(targetId);
                Long commentUserId = comment.getUser().getId();
                Long userId = userService.findByUsername(userDetails.getUsername()).getId();

                if (commentUserId.equals(userId)) {
                    log.info("User {} successfully passed the ownership check for comment {}", userId, targetId);
                    Object result = proceedingJoinPoint.proceed();
                    log.info("Method execution completed: {}", methodName);
                    return result;
                } else {
                    log.warn("User {} failed the ownership check for comment {}", userId, targetId);
                    throw new AccessDeniedException("You are not the owner of this comment.");
                }
            }
        }

        throw new AccessDeniedException("Request attributes not found");
    }

    private UserDetails getUserDetailsFromArgs(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof UserDetails) {
                return (UserDetails) arg;
            }
        }
        return null;
    }
}
