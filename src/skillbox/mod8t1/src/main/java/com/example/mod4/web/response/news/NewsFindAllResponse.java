package com.example.mod4.web.response.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsFindAllResponse {

    private Long id;

    private String title;

    private String description;

    private String body;

    private String username;

    private Instant createdAt;

    private Instant updatedAt;

    private Integer countComment;
}