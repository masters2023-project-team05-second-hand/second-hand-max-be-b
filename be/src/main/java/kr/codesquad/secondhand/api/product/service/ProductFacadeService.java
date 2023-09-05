package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.Status;
import kr.codesquad.secondhand.api.product.dto.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final ImageService imageService;

    @Transactional
    public ProductReadResponse readProduct(long memberId, Long productId) {
        Product product = productService.findById(productId);
        List<ProductImage> productImages = imageService.findAllByProductId(productId);
        boolean isSeller = product.isSellerIdEqualsTo(memberId);
        List<Status> statuses = Status.findAll();

        return ProductReadResponse.of(isSeller, product, productImages, statuses);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        // 주의: Entity 영속성으로 인해 순서 바뀌면 이미지 삭제 안됨
        imageService.deleteProductImages(productId);
        productService.deleteProduct(productId);
    }
}
