package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import kr.codesquad.secondhand.api.product.repository.ProductStatRepository;
import kr.codesquad.secondhand.api.product.repository.ProductStatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductStatService {

    private final ProductStatRepository productStatRepository;
    private final ProductStatRedisRepository productStatRedisRepository;

    @Transactional
    public void saveNewProductStats(Long productId) {
        productStatRedisRepository.saveNewProductStats(productId);
    }

    @Transactional
    public void saveAll(List<ProductStat> productStats) {
        productStatRepository.saveAll(productStats);
    }

    @Transactional
    public void increaseViews(Long productId) {
        productStatRedisRepository.increaseViews(productId);
    }


    public ProductStat findProductStat(Long productId) {
        cacheProductStatNotExistsInRedis(productId);
        return productStatRedisRepository.findProductStat(productId);
    }

    public Map<Long, ProductStat> findProductsStats(List<Product> products) {
        return products.stream()
                .map(product -> findProductStat(product.getId()))
                .collect(Collectors.toUnmodifiableMap(
                        productStat -> productStat.getId(),
                        productStat -> productStat)
                );
    }

    public Set<Long> getProductKeys() {
        return productStatRedisRepository.getProductKeys();
    }

    public void cacheProductStatNotExistsInRedis(Long productId) {
        if (!productStatRedisRepository.hasProductKey(productId)) {
            cacheProductStat(productId);
        }
    }

    public void cacheProductStat(Long productId) {
        Optional<ProductStat> productStat = productStatRepository.findById(productId);
        productStat.ifPresent(productStatRedisRepository::saveProductStat);
    }

    public void increaseWishes(Long productId) {
        productStatRedisRepository.increaseWishes(productId);
    }

    public void decreaseWishes(Long productId) {
        productStatRedisRepository.decreaseWishes(productId);
    }

    public void flushAllRedis() {
        productStatRedisRepository.flushAllRedis();
    }
}
