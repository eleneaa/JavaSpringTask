package com.example.mod7.web.Task;

import com.example.mod7.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String name;

    private String description;

    private String authorId;

    private String assigneeId;

    private Set<String> observerIds;

    private TaskStatus status;
}
