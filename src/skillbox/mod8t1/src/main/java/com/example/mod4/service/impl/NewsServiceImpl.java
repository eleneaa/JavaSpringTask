package com.example.mod4.service.impl;

import com.example.mod4.aop.AccessibleDeleteNews;
import com.example.mod4.aop.AccessibleUpdateNews;
import com.example.mod4.model.Category;
import com.example.mod4.model.News;
import com.example.mod4.model.User;
import com.example.mod4.repository.NewsRepository;
import com.example.mod4.repository.NewsSpecification;
import com.example.mod4.service.CategoryService;
import com.example.mod4.service.NewsService;
import com.example.mod4.service.UserService;
import com.example.mod4.utils.BeanUtils;
import com.example.mod4.web.request.NewsFilterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {


    private final NewsRepository newsRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public List<News> findAll() {
        log.debug("NewsServiceImpl -> findAll");
        return newsRepository.findAll();
    }

    @Override
    public Page<News> findAll(int pageNumber, int pageSize) {
        log.debug("NewsServiceImpl -> findAll pageNumber = {}, pageSize = {}", pageNumber, pageSize);
        return newsRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Override
    public News findById(Long id) {
        log.debug("NewsServiceImpl -> findById id = {}", id);
        return newsRepository.findById(id).orElse(null);
    }


    @Override
    public News save(News news) {
        log.debug("NewsServiceImpl -> save news = {}", news);
        User user = userService.findById(news.getUser().getId());
        Category category = categoryService.findById(news.getCategory().getId());
        user.addNews(news);
        category.addNews(news);
        return newsRepository.save(news);
    }

    @Override
    @AccessibleUpdateNews
    public News update(News news) {
        log.debug("NewsServiceImpl -> update news= {}", news);
        User user = userService.findById(news.getUser().getId());
        Category category = categoryService.findById(news.getCategory().getId());
        News existedNews = findById(news.getId());
        BeanUtils.copyNonNullProperties(news, existedNews);
        existedNews.setUser(user);
        existedNews.setCategory(category);
        return newsRepository.save(existedNews);
    }


    @Override
    @AccessibleDeleteNews
    @Transactional
    public News deleteByIdAndUserId(Long id, Long userId) {
        log.debug("NewsServiceImpl->deleteByIdAndUserId id= {}, userId= {}", id, userId);
        News deletedNews = newsRepository.findById(id).orElse(null);
        newsRepository.deleteByIdAndUserId(id, userId);
        return deletedNews;
    }

    @Override
    public Page<News> filterBy(NewsFilterRequest filter) {
        return newsRepository.findAll(NewsSpecification.withFilter(filter)
                , PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
    }
}