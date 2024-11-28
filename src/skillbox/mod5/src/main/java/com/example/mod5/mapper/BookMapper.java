package com.example.mod5.mapper;

import com.example.mod5.model.Book;
import com.example.mod5.web.BookResponse;
import com.example.mod5.web.UpsertBookRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    @Mapping(source = "category.categoryName", target = "categoryName")
    BookResponse bookToResponse(Book book);

    @Mapping(source = "categoryName", target = "category.categoryName")
    Book requestToBook(UpsertBookRequest request);

}