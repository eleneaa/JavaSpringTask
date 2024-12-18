package com.example.mod4.mapper;

import com.example.mod4.model.Category;
import com.example.mod4.model.News;
import com.example.mod4.service.CategoryService;
import com.example.mod4.service.CommentService;
import com.example.mod4.service.UserService;
import com.example.mod4.web.request.UpsertNewsRequest;
import com.example.mod4.web.response.news.NewsFindAllResponse;
import com.example.mod4.web.response.news.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public News requestToNews(UpsertNewsRequest request) {
        News news = new News();
        news.setTitle(request.getTitle());
        news.setDescription(request.getDescription());
        news.setBody(request.getBody());
        Category category = categoryService.findByName(request.getCategoryName());
        if (category == null) {
            category = new Category();
            category.setName(request.getCategoryName());
            category = categoryService.save(category);
        }
        news.setCategory(category);
        news.setUser(userService.findById(request.getUserId()));
        news.setComments(commentService.findAllByUserId(request.getUserId()));
        return news;
    }

    @Override
    public NewsFindAllResponse newsFindAllToResponse(News news) {
        NewsFindAllResponse response = newsMapper.newsFindAllToResponse(news);
        response.setCountComment(news.getComments().size());
        response.setUsername(news.getUser().getUsername());
        return response;
    }

    @Override
    public NewsResponse newsToResponse(News news) {
        NewsResponse response = newsMapper.newsToResponse(news);
        response.setCountComment(news.getComments().size());
        response.setUsername(news.getUser().getUsername());
        response.setComments(news.getComments().stream()
                .map(it -> commentMapper.commentToResponse(it))
                .toList());

        return response;

    }

    @Override
    public News requestToNews(Long newsId, UpsertNewsRequest request) {
        News news = requestToNews(request);
        news.setId(newsId);
        return news;
    }
}

