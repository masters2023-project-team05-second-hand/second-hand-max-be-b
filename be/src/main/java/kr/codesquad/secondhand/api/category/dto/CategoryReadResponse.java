package kr.codesquad.secondhand.api.category.dto;

import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Getter;

@Getter
public class CategoryReadResponse {

    private final Long id;
    private final String name;
    private final String imgUrl;

    public CategoryReadResponse(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public static CategoryReadResponse from(Category category) {
        return new CategoryReadResponse(category.getId(), category.getName(), category.getImgUrl());
    }
}
