package com.example.mod4.mapper;

import com.example.mod4.model.News;
import com.example.mod4.web.request.UpsertNewsRequest;
import com.example.mod4.web.response.news.NewsFindAllResponse;
import com.example.mod4.web.response.news.NewsResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(NewsMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public interface NewsMapper {

    News requestToNews(UpsertNewsRequest request);

    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, UpsertNewsRequest request);

    @Mapping(source = "user.username", target = "username")
    NewsResponse newsToResponse(News news);

    @Mapping(source = "user.username", target = "username")
    NewsFindAllResponse newsFindAllToResponse(News news);
}