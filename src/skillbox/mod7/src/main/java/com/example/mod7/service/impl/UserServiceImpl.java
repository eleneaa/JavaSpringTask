package com.example.mod7.service.impl;

import com.example.mod7.exeption.EntityNotFoundException;
import com.example.mod7.mapper.UserMapper;
import com.example.mod7.model.User;
import com.example.mod7.repository.TaskRepository;
import com.example.mod7.repository.UserRepository;
import com.example.mod7.service.UserService;
import com.example.mod7.web.User.UserRequest;
import com.example.mod7.web.User.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.ls.LSOutput;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TaskRepository taskRepository;

    @Override
    public Flux<UserResponse> findAll() {
        return userRepository.findAll().map(userMapper::toDto);
    }

    @Override
    public Mono<UserResponse> findById(String id) {
        Mono<User> byId = userRepository.findById(id);
        return byId
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found with id: " + id)))
                .map(userMapper::toDto);

    }


    @Override
    public Mono<UserResponse> create(UserRequest user) {
        return userRepository.save(userMapper.toEntity(user)).map(userMapper::toDto);
    }

    @Override
    public Mono<UserResponse> update(String id, UserRequest userDto) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new EntityNotFoundException("User not found with id: " + id)))
                .flatMap(user -> {

                    user.setName(userDto.getName());
                    user.setEmail(userDto.getEmail());
                    return userRepository.save(user);
                })
                .map(userMapper::toDto);
    }


    @Override
    public Mono<Void> deleteById(String id) {
        return taskRepository.findAllByAuthorId(id)
                .concatWith(taskRepository.findAllByAssigneeId(id))
                .concatWith(taskRepository.findAllByObserverIdsContaining(id))
                .flatMap(task -> taskRepository.deleteById(task.getId()))
                .then(userRepository.deleteById(id));
    }


    @Override
    public Flux<User> findAllById(Set<String> observerIds) {
        return userRepository.findAllById(observerIds)
                .collectList()
                .flatMapMany(users -> {
                    if (users.isEmpty()) {
                        log.warn("No users found with ids: {}", observerIds);
                        return Flux.empty();
                    } else {
                        return Flux.fromIterable(users);
                    }
                });
    }
}
