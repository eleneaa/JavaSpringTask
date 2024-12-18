package com.example.mod4.web.controller;

import com.example.mod4.aop.CheckOwnership;
import com.example.mod4.model.News;
import com.example.mod4.exception.EntityNotFoundException;
import com.example.mod4.mapper.NewsMapper;
import com.example.mod4.service.NewsService;
import com.example.mod4.web.request.NewsFilterRequest;
import com.example.mod4.web.request.UpsertNewsRequest;
import com.example.mod4.web.response.ErrorResponse;
import com.example.mod4.web.response.news.NewsListResponse;
import com.example.mod4.web.response.news.NewsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/news")
@Tag(name = "News Controller")
public class NewsController {

    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @Operation(
            summary = "Filtering news by categories and username",
            description = "Filtering news by categories and username"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> filterBy(NewsFilterRequest filterRequest) {
        Page<News> news = newsService.filterBy(filterRequest);
        return ResponseEntity.ok(
                NewsListResponse.builder()
                        .totalCount(news.getTotalElements())
                        .newsResponseList(news.stream().map(newsMapper::newsFindAllToResponse).toList())
                        .build()
        );
    }

    @Operation(
            summary = "Get all news",
            description = "Get all news and pagination(page number, page size)"
    )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = NewsListResponse.class), mediaType = "application/json")
            }
    )
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping
    public ResponseEntity<NewsListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {

        Page<News> news = newsService.findAll(pageNumber, pageSize);
        return ResponseEntity.ok(
                NewsListResponse.builder()
                        .totalCount(news.getTotalElements())
                        .newsResponseList(news.stream().map(newsMapper::newsFindAllToResponse).toList())
                        .build()
        );
    }

    @Operation(
            summary = "Get news bu id",
            description = "Get news by id. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponse> findById(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news != null) {
            return ResponseEntity.ok(newsMapper.newsToResponse(news));
        }
        throw new EntityNotFoundException(MessageFormat.format("news with id = {0} not found", id));
    }

    @Operation(
            summary = "Create new news",
            description = "create new news. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping
    public ResponseEntity<NewsResponse> create(@RequestBody @Valid UpsertNewsRequest request) {
        News newNews = newsService.save(newsMapper.requestToNews(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(newsMapper.newsToResponse(newNews));
    }

    @Operation(
            summary = "Update news by id",
            description = "Update news by id. Return tittle, description, body, createAt, updateAt, count comment and list comments"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {
                            @Content(schema = @Schema(implementation = NewsResponse.class), mediaType = "application/json")
                    }
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {
                            @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
                    }
            )
    })
    @CheckOwnership
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PutMapping("/{id}")
    public ResponseEntity<NewsResponse> update(@PathVariable("id") Long newsId,
                                               @RequestBody @Valid UpsertNewsRequest request) {
        News updateNews = newsService.update(newsMapper.requestToNews(newsId, request));
        if (updateNews != null) {
            return ResponseEntity.ok(newsMapper.newsToResponse(updateNews));
        }
        throw new EntityNotFoundException(MessageFormat.format("news with id = {0} not found", newsId));
    }

    @Operation(
            summary = "Removing news by id",
            description = "Removing news by id"
    )
    @CheckOwnership
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @RequestParam("userId") Long userId) {
        News deleteNews = newsService.deleteByIdAndUserId(id,userId);
        if (deleteNews != null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("news with id = {0} not found", id));
    }
}