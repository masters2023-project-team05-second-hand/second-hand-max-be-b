package kr.codesquad.secondhand.api.category.controller;

import java.util.List;
import kr.codesquad.secondhand.api.category.dto.CategoryReadResponse;
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
    public ResponseEntity<List<CategoryReadResponse>> readCategories() {
        List<CategoryReadResponse> categoryReadResponse = categoryService.findCategories();
        return ResponseEntity.ok()
                .body(categoryReadResponse);
    }
}
