package com.example.mod7.service;

import com.example.mod7.web.Task.TaskRequest;
import com.example.mod7.web.Task.TaskResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TaskService {

    Flux<TaskResponse> findAll();

    Mono<TaskResponse> findById(String id);

    Mono<TaskResponse> create(TaskRequest task);

    Mono<TaskResponse> update(String id, TaskRequest updatedTask);

    Mono<Void> deleteById(String id);

    Mono<TaskResponse> addObserver(String id, String observerId);
}
