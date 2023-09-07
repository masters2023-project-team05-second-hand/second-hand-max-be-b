package kr.codesquad.secondhand.api.product.service;

import java.net.URL;
import kr.codesquad.secondhand.api.address.domain.Address;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductStatusUpdateRequest;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    @Transactional
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    @Transactional
    public void updateProduct(Product product, String title, String content, Long price, Address address,
                              Category category,
                              URL thumbnailImgUrl) {
        product.updateProduct(title, content, price, address, category, thumbnailImgUrl);
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusUpdateRequest request) {
        ProductStatus productStatus = ProductStatus.from(request.getStatusId());
        Product product = productRepository.findById(productId).orElseThrow();
        product.updateStatus(productStatus);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
