package com.example.mod4.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertCommentRequest {

    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @NotNull(message = "News ID cannot be empty")
    private Long newsId;

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

}