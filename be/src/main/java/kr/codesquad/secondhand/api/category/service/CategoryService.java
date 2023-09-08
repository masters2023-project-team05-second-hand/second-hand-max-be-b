package kr.codesquad.secondhand.api.category.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategoryReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<CategoryReadResponse> findCategories() {
        List<Category> categories = Category.findAll();
        return categories.stream()
                .map(category -> new CategoryReadResponse(category.getId(), category.getName(), category.getImgUrl()))
                .collect(Collectors.toUnmodifiableList());
    }
}
