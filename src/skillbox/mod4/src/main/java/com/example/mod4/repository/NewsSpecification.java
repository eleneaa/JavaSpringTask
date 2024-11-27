package com.example.mod4.repository;

import com.example.mod4.model.Category;
import com.example.mod4.model.News;
import com.example.mod4.model.User;
import com.example.mod4.web.request.NewsFilterRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface NewsSpecification {

    static Specification<News> withFilter(NewsFilterRequest filter) {
        return Specification.where(
                        byCategoryName(filter.getCategoryName()))
                .and(byUsername(filter.getUsername()));
    }

    static Specification<News> byCategoryName(String categoryName) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(categoryName)) {
                return null;
            }

            return criteriaBuilder.equal(root.get(News.Fields.category).get(Category.Fields.name), categoryName);
        };
    }

    static Specification<News> byUsername(String username) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(username)) {
                return null;
            }
            return criteriaBuilder.equal(root.get(News.Fields.user).get(User.Fields.username), username);
        };
    }
}