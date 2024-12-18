package com.example.mod4.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertNewsRequest {

    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @NotBlank(message = "The news title cannot be empty")
    private String title;

    private String description;

    @NotBlank(message = "The news body cannot be empty")
    private String body;

    @NotBlank(message = "News category name cannot be empty")
    private String categoryName;
}