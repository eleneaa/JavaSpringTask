package com.example.mod7.web.Task;

import com.example.mod7.model.TaskStatus;
import com.example.mod7.web.User.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {

    private String id;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds;

    private TaskStatus status;

    private UserResponse author;
    private UserResponse assignee;
    private Set<UserResponse> observers;
}
