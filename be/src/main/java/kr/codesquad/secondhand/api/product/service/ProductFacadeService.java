package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductImage;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final ImageService imageService;
    private final StatusService statusService;

    @Transactional
    public ProductReadResponse readProduct(long memberId, Long productId) {
        Product product = productService.findById(productId);
        List<ProductImage> productImages = imageService.findAllByProductId(productId);
        boolean isSeller = product.isSellerIdEqualsTo(memberId);
        List<ProductStatus> statuses = statusService.findAll();

        return ProductReadResponse.of(isSeller, product, productImages, statuses);
    }
}
