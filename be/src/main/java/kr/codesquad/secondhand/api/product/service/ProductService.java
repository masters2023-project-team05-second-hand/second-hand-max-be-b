package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.ProductStatusUpdateRequest;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    public Slice<Product> findByCategoryIdAndIdIn(List<Long> productIds, Long categoryId, Pageable pageable){
        return productRepository.findByCategoryIdAndIdIn(productIds, categoryId, pageable);
    }

    public Slice<Product> findBySellerIdAndStatusIds(Long sellerId, List<Integer> statusIds, PageRequest pageRequest) {
        return productRepository.findBySellerIdAndStatusIds(sellerId, statusIds, pageRequest);
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
