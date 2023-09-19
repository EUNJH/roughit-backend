package com.roufit.backend.domain.exercise.dto.request;

import com.roufit.backend.domain.exercise.domain.category.Category;
import lombok.Data;

@Data
public class CategoryRequest {

    private Long parentId;
    private String title;

    public Category toEntity(Category parent) {
        return Category.builder()
                .parent(parent)
                .title(title)
                .build();
    }
}