package com.example.mod4.web.response.category;

import com.example.mod4.web.response.news.NewsCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    private Long id;

    private String name;

    private List<NewsCategoryResponse> news = new ArrayList<>();
}