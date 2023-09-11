package kr.codesquad.secondhand.api.category.service;

import java.util.List;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.category.dto.CategoryReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<CategoryReadResponse> findCategories() {
        return CategoryReadResponse.from(Category.findAll());
    }
}
