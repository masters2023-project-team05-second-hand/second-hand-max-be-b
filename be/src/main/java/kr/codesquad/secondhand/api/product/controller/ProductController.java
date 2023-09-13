package kr.codesquad.secondhand.api.product.controller;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractMemberId;

import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.product.dto.request.ProductCreateRequest;
import kr.codesquad.secondhand.api.product.dto.request.ProductStatusUpdateRequest;
import kr.codesquad.secondhand.api.product.dto.request.ProductUpdateRequest;
import kr.codesquad.secondhand.api.product.dto.response.ProductCreateResponse;
import kr.codesquad.secondhand.api.product.dto.response.ProductReadResponse;
import kr.codesquad.secondhand.api.product.dto.response.ProductSlicesResponse;
import kr.codesquad.secondhand.api.product.service.ProductFacadeService;
import kr.codesquad.secondhand.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacadeService productFacadeService;
    private final ProductService productService;

    @PostMapping("/api/products")
    public ResponseEntity<ProductCreateResponse> createProduct(HttpServletRequest httpServletRequest,
                                                               @Validated @ModelAttribute ProductCreateRequest productCreateRequest) {
        Long memberId = extractMemberId(httpServletRequest);
        ProductCreateResponse productCreateResponse = productFacadeService.saveProduct(memberId, productCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productCreateResponse);
    }

    @GetMapping("/api/products/{productId}")
    public ResponseEntity<ProductReadResponse> readProduct(HttpServletRequest httpServletRequest,
                                                           @PathVariable Long productId) {
        Long memberId = extractMemberId(httpServletRequest);
        ProductReadResponse productReadResponse = productFacadeService.readProduct(memberId, productId);
        return ResponseEntity.ok()
                .body(productReadResponse);
    }

    @GetMapping("/api/products")
    public ResponseEntity<ProductSlicesResponse> readProducts(@RequestParam Long addressId,
                                                              @RequestParam Long categoryId,
                                                              @RequestParam Long cursor,
                                                              @RequestParam Integer size) {
        ProductSlicesResponse response = productFacadeService.readProducts(cursor, addressId, categoryId, size);
        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/api/products/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId,
                                                @Validated @ModelAttribute ProductUpdateRequest productUpdateRequest) {
        productFacadeService.updateProduct(productId, productUpdateRequest);
        return ResponseEntity.ok()
                .build();
    }

    @PatchMapping("/api/products/{productId}/status")
    public ResponseEntity<String> updateProductStatus(@PathVariable Long productId,
                                                      @Validated @RequestBody ProductStatusUpdateRequest request) {
        productService.updateProductStatus(productId, request);
        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/api/products/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productFacadeService.deleteProduct(productId);
        return ResponseEntity.ok()
                .build();
    }
}
