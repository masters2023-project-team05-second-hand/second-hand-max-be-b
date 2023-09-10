package kr.codesquad.secondhand.api.category.dto;

import java.util.List;
import java.util.stream.Collectors;
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

    public static List<CategoryReadResponse> from(List<Category> categories) {
        return categories.stream()
                .map(category -> new CategoryReadResponse(category.getId(), category.getName(), category.getImgUrl()))
                .collect(Collectors.toUnmodifiableList());
    }
}
