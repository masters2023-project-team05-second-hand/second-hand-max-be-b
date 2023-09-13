package kr.codesquad.secondhand.api.category.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import lombok.Getter;

@Getter
public class CategorySummaryResponse {

    private final Long id;
    private final String name;

    private CategorySummaryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static List<CategorySummaryResponse> from(List<Category> categories){
        return categories.stream()
                .map(category -> new CategorySummaryResponse(category.getId(), category.getName()))
                .collect(Collectors.toUnmodifiableList());
    }
}
