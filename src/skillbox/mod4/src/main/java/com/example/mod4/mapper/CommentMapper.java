package com.example.mod4.mapper;

import com.example.mod4.model.Comment;
import com.example.mod4.web.request.UpsertCommentRequest;
import com.example.mod4.web.response.comment.CommentResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(CommentMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {

    Comment requestToComment(UpsertCommentRequest request);

    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, UpsertCommentRequest request);

    @Mapping(source = "user.username", target = "username")
    CommentResponse commentToResponse(Comment comment);
}