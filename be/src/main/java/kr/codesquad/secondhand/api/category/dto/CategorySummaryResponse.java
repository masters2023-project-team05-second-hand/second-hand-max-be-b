package kr.codesquad.secondhand.api.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Getter;

/**
 * 상품 상세 조회, 관심 상품 목록 조회에서 공통으로 사용
 */
@Getter
public class CategorySummaryResponse {

    private final Long id;
    private final String name;

    private CategorySummaryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategorySummaryResponse from(Category category){
        return new CategorySummaryResponse(category.getId(), category.getName());
    }

    public static List<CategorySummaryResponse> from(List<Category> categories){
        return categories.stream()
                .map(CategorySummaryResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
