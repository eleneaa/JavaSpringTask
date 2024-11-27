package com.example.mod4.service;

import com.example.mod4.model.News;
import com.example.mod4.web.request.NewsFilterRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsService {

    List<News> findAll();

    Page<News> findAll(int pageNumber, int PageSize);

    News findById(Long id);

    News save(News news);

    News update(News ost);

    News deleteByIdAndUserId(Long id, Long userId);

    Page<News> filterBy(NewsFilterRequest filter);

}
