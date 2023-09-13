package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.category.domain.Category;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.dto.request.ProductStatusUpdateRequest;
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

    private static final int DEFAULT_PAGE = 0;

    private final ProductRepository productRepository;

    public Long saveProduct(Product product) {
        productRepository.save(product);
        return product.getId();
    }

    public Product findById(Long productId) {
        return productRepository.findById(productId).orElseThrow();
    }

    public Slice<Product> findWishedProductByCategoryIdAndIdIn(List<Long> productIds, Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndIdIn(productIds, categoryId, pageable);
    }

    public Slice<Product> findBySellerIdAndStatusIds(Long sellerId, List<Integer> statusIds, PageRequest pageRequest) {
        return productRepository.findBySellerIdAndStatusIds(sellerId, statusIds, pageRequest);
    }

    public List<Long> findCategoryIdsByIdIn(List<Long> productIds) {
        return productRepository.findCategoryIdsByIdIn(productIds);
    }

    public Slice<Product> findByAddressIdAndCategoryId(Long cursor, Long addressId, Long categoryId, Integer size) {
        Category category = Category.from(categoryId);
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, size);
        return productRepository.findByAddressIdAndCategoryId(cursor, addressId, category.getId(), pageRequest);
    }

    @Transactional
    public void updateProductStatus(Long productId, ProductStatusUpdateRequest request) {
        ProductStatus productStatus = ProductStatus.from(request.getStatusId());
        Product product = productRepository.findById(productId).orElseThrow();
        product.updateStatus(productStatus);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
