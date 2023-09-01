package kr.codesquad.secondhand.api.category.dto;

import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private String imgUrl;

    public CategoryResponse(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getImgUrl());
    }
}
