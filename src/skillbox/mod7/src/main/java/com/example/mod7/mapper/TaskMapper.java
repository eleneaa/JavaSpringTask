package com.example.mod7.mapper;

import com.example.mod7.model.Task;
import com.example.mod7.model.User;
import com.example.mod7.web.Task.TaskRequest;
import com.example.mod7.web.Task.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface TaskMapper {

    TaskResponse toDto(Task task);

    @Mapping(target = "id", expression = "java(generateId())")
    Task toEntity(TaskRequest task);

    default Mono<TaskResponse> toDto(Task task, Mono<User> authorMono, Mono<User> assigneeMono, Flux<User> observersFlux, UserMapper userMapper) {
        return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                .map(tuple -> {
                    User author = tuple.getT1();
                    User assignee = tuple.getT2();
                    List<User> observers = tuple.getT3();

                    TaskResponse taskResponse = toDto(task);
                    taskResponse.setAuthor(userMapper.toDto(author));
                    taskResponse.setAssignee(userMapper.toDto(assignee));
                    taskResponse.setObservers(observers.stream()
                            .map(userMapper::toDto)
                            .collect(Collectors.toSet()));
                    return taskResponse;
                });
    }

    default String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
}
