package com.example.mod4.web.response.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsListResponse {

    private Long totalCount;

    private List<NewsFindAllResponse> newsResponseList = new ArrayList<>();
}