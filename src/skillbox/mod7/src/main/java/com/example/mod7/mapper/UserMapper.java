package com.example.mod7.mapper;

import com.example.mod7.model.User;
import com.example.mod7.web.User.UserRequest;
import com.example.mod7.web.User.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "id", expression = "java(generateId())")
    User toEntity(UserRequest user);

    UserResponse toDto(User user);

    User toUser(UserResponse userResponse);

    default String generateId() {
        return java.util.UUID.randomUUID().toString();
    }
}
