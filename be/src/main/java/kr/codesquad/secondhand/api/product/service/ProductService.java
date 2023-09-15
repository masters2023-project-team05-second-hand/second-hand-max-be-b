package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStatus;
import kr.codesquad.secondhand.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

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
//        Category category = Category.from(categoryId); 0일 경우 버그가 발생할 수 있어서 다른 검증 방식 필요
        PageRequest pageRequest = PageRequest.of(DEFAULT_PAGE, size);
        return productRepository.findByAddressIdAndCategoryId(cursor, addressId, categoryId, pageRequest);
    }

    public void updateProductStatus(Long productId, ProductStatus productStatus) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.updateStatus(productStatus);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
