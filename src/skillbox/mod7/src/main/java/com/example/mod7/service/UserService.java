package com.example.mod7.service;

import com.example.mod7.model.User;
import com.example.mod7.web.User.UserRequest;
import com.example.mod7.web.User.UserResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Set;

public interface UserService {

    Flux<UserResponse> findAll();

    Mono<UserResponse> findById(String id);

    Mono<UserResponse> create(UserRequest user);

    Mono<UserResponse> update(String id, UserRequest updatedUser);

    Mono<Void> deleteById(String id);

    Flux<User> findAllById(Set<String> observerIds);
}
