package kr.codesquad.secondhand.api.product.controller;

import java.io.IOException;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.ProductModifyRequest;
import kr.codesquad.secondhand.api.product.dto.ProductReadResponse;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/products")
    public ResponseEntity<ProductCreateResponse> createProduct(@ModelAttribute ProductCreateRequest productCreateRequest)
            throws IOException {
        //TODO 인증 필터 구현 시 토큰에서 memberId 받아올 예정
        Long memberId = 1L;
        ProductCreateResponse productCreateResponse = productService.saveProduct(productCreateRequest, memberId);
        return ResponseEntity.ok()
                .body(productCreateResponse);
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductReadResponse> readProduct(@PathVariable Long productId) {
        ProductReadResponse productReadResponse = productService.readProduct(1L, productId);
        return ResponseEntity.ok()
                .body(productReadResponse);
    }

    @PatchMapping("/api/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId,
                                                @ModelAttribute ProductModifyRequest productModifyRequest) throws IOException {
        productService.updateProduct(productId, productModifyRequest);
        return ResponseEntity.ok()
                .build();
    }
}
