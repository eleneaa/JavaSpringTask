package com.example.mod4.mapper;

import com.example.mod4.model.Category;
import com.example.mod4.web.request.UpsertCategoryRequest;
import com.example.mod4.web.response.category.CategoryFindAllResponse;
import com.example.mod4.web.response.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {NewsMapper.class})
public interface CategoryMapper {

    Category requestToCategory(UpsertCategoryRequest request);

    @Mapping(source = "categoryId", target = "id")
    Category requestToCategory(Long categoryId, UpsertCategoryRequest request);

    CategoryResponse categoryToResponse(Category category);

    CategoryFindAllResponse categoryFindAllToResponse(Category category);

}