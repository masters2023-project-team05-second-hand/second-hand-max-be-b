package kr.codesquad.secondhand.api.category.controller;

import java.util.List;
import kr.codesquad.secondhand.api.category.dto.CategoryResponse;
import kr.codesquad.secondhand.api.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("api/categories")
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getCategories();
        return ResponseEntity.ok()
                .body(categoryResponses);
    }
}
