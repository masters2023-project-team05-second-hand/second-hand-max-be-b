package kr.codesquad.secondhand.api.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryReadResponse {

    private final Long id;
    private final String name;
    private final String imgUrl;

    @Builder
    private CategoryReadResponse(Long id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public static CategoryReadResponse from(Category category) {
        return CategoryReadResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .imgUrl(category.getImgUrl())
                .build();
    }

    public static List<CategoryReadResponse> from(List<Category> categories) {
        return categories.stream()
                .map(CategoryReadResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
