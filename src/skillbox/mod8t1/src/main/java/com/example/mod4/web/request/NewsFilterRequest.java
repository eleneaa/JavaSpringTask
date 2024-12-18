package com.example.mod4.web.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsFilterRequest {

    private Integer pageSize;

    private Integer pageNumber;

    private String categoryName;

    private String username;
}