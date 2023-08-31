package kr.codesquad.secondhand.api.product.controller;

import java.io.IOException;
import kr.codesquad.secondhand.api.product.dto.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/products")
    public ResponseEntity<ProductCreateResponse> uploads3(@ModelAttribute ProductCreateRequest productCreateRequest)
            throws IOException {
        ProductCreateResponse productCreateResponse = productService.save(productCreateRequest);
        return ResponseEntity.ok(productCreateResponse);
    }
}
