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
public class NewsCategoryResponse {

    private Long id;

    private String title;

    private Instant createdAt;
}