package com.example.mod7.service.impl;

import com.example.mod7.exeption.EntityNotFoundException;
import com.example.mod7.mapper.TaskMapper;
import com.example.mod7.mapper.UserMapper;
import com.example.mod7.model.Task;
import com.example.mod7.model.User;
import com.example.mod7.repository.TaskRepository;
import com.example.mod7.repository.UserRepository;
import com.example.mod7.service.TaskService;
import com.example.mod7.service.UserService;
import com.example.mod7.web.Task.TaskRequest;
import com.example.mod7.web.Task.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;
    private final UserService userService;


    @Override
    public Flux<TaskResponse> findAll() {
        Flux<Task> all = taskRepository.findAll();
        return all
                .flatMap(task -> {
                    validateTask(task);
                    Mono<User> authorMono = userService.findById(task.getAuthorId())
                            .map(userMapper::toUser);
                    Mono<User> assigneeMono = userService.findById(task.getAssigneeId())
                            .map(userMapper::toUser);
                    Flux<User> observersFlux = userService.findAllById(task.getObserverIds());

                    return taskMapper.toDto(task, authorMono, assigneeMono, observersFlux, userMapper);
                });
    }

    @Override
    public Mono<TaskResponse> findById(String id) {
        Mono<Task> taskResponseMono = taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found with id: " + id)));
        return taskResponseMono
                .flatMap(task -> {
                    validateTask(task);
                    Mono<User> authorMono = userService.findById(task.getAuthorId())
                            .map(userMapper::toUser);

                    Mono<User> assigneeMono = userService.findById(task.getAssigneeId())
                            .map(userMapper::toUser);
                    // Получаем список наблюдателей задачи
                    Flux<User> observersFlux = userService.findAllById(task.getObserverIds());

                    // Маппим задачу в TaskDTO с помощью TaskMapper
                    return taskMapper.toDto(task, authorMono, assigneeMono, observersFlux, userMapper);
                });
    }

    @Override
    public Mono<TaskResponse> create(TaskRequest taskRequest) {
        return userRepository.findById(taskRequest.getAuthorId())
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Author not found with id: " + taskRequest.getAuthorId())))
                .flatMap(author ->
                        userRepository.findById(taskRequest.getAssigneeId())
                                .switchIfEmpty(Mono.error(new EntityNotFoundException("Assignee not found with id: " + taskRequest.getAssigneeId())))
                                .flatMap(assignee -> {
                                    Task task = taskMapper.toEntity(taskRequest);
                                    task.setCreatedAt(Instant.now());
                                    task.setUpdatedAt(Instant.now());

                                    return taskRepository.save(task)
                                            .flatMap(savedTask -> findById(savedTask.getId()));
                                })
                );
    }


    @Override
    public Mono<TaskResponse> update(String id, TaskRequest taskRequest) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found with id: " + id)))
                .flatMap(existingTask -> {
                    Task updatedTask = updateTaskFields(existingTask, taskRequest);
                    updatedTask.setUpdatedAt(Instant.now());
                    return taskRepository.save(updatedTask);
                })
                .flatMap(savedTask -> findById(savedTask.getId()));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found with id: " + id)))
                .flatMap(existingTask -> taskRepository.deleteById(id));
    }


    @Override
    public Mono<TaskResponse> addObserver(String taskId, String observerId) {
        return taskRepository.findById(taskId)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Task not found with id: " + taskId)))
                .flatMap(task ->
                        userRepository.findById(observerId)
                                .switchIfEmpty(Mono.error(new EntityNotFoundException("Observer not found with id: " + observerId)))
                                .flatMap(observer -> {
                                    task.getObserverIds().add(observerId);
                                    task.getObservers().add(observer);
                                    return taskRepository.save(task);
                                })
                )
                .flatMap(savedTask -> findById(savedTask.getId()));
    }




    public void validateTask(Task task) {
        if (task.getAuthorId() == null) {
            throw new EntityNotFoundException("Author ID is missing");
        }
        if (task.getAssigneeId() == null) {
            throw new EntityNotFoundException("Assignee ID is missing");
        }
        if (task.getObserverIds() == null) {
            throw new EntityNotFoundException("Observer IDs are missing");
        }
    }

    private Task updateTaskFields(Task existingTask, TaskRequest taskRequest) {
        Task updatedTask = new Task();
        updatedTask.setId(existingTask.getId());
        updatedTask.setAuthorId(
                taskRequest.getAuthorId() != null ? taskRequest.getAuthorId() : existingTask.getAuthorId());
        updatedTask.setAssigneeId(
                taskRequest.getAssigneeId() != null ? taskRequest.getAssigneeId() : existingTask.getAssigneeId());
        updatedTask.setObserverIds(
                taskRequest.getObserverIds() != null ? taskRequest.getObserverIds() : existingTask.getObserverIds());
        updatedTask.setName(taskRequest.getName() != null ? taskRequest.getName() : existingTask.getName());
        updatedTask.setDescription(
                taskRequest.getDescription() != null ? taskRequest.getDescription() : existingTask.getDescription());
        updatedTask.setStatus(taskRequest.getStatus() != null ? taskRequest.getStatus() : existingTask.getStatus());
        return updatedTask;
    }
}
