package kr.codesquad.secondhand.api.product.controller;

import java.io.IOException;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/products")
    public ResponseEntity<ProductCreateResponse> uploads3(@ModelAttribute ProductCreateRequest productCreateRequest)
            throws IOException {
        Long memberId = 1L;
        ProductCreateResponse productCreateResponse = productService.save(productCreateRequest, memberId);
        return ResponseEntity.ok().body(productCreateResponse);
    }
}
