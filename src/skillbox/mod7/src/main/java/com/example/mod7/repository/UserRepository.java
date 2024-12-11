package com.example.mod7.repository;

import com.example.mod7.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByName(String name);
}
