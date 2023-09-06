package kr.codesquad.secondhand.api.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Getter;

@Getter
public class CategoriesReadResponse {

    private final List<CategoryResponse> categories;

    public CategoriesReadResponse(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public static CategoriesReadResponse from(List<Category> categories) {
        return new CategoriesReadResponse(categories.stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toUnmodifiableList())
        );
    }

    @Getter
    private static class CategoryResponse {

        private final Long id;
        private final String name;
        private final String imgUrl;

        private CategoryResponse(Long id, String name, String imgUrl) {
            this.id = id;
            this.name = name;
            this.imgUrl = imgUrl;
        }

        private static CategoryResponse from(Category category) {
            return new CategoryResponse(category.getId(), category.getName(), category.getImgUrl());
        }
    }
}
