package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import java.util.Map;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import kr.codesquad.secondhand.api.product.repository.StatRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatService {

    private final StatRedisRepository statRedisRepository;

    public void saveNewProductStats(Long productId) {
        statRedisRepository.saveNewProductStats(productId);
    }

    public ProductStats findProductStats(Long productId) {
        return statRedisRepository.findProductStats(productId);
    }

    public Map<Long, ProductStats> findProductsStats(List<Product> products) {
        return statRedisRepository.findProductsStats(products);
    }

    public List<Long> findWishlistByMemberId(Long memberId) {
        List<Long> wishlist = statRedisRepository.findMemberWishedProducts(memberId);
        return wishlist;
    }

    @Transactional
    public void increaseViews(Long productId, Long memberId) {
        increaseViewsAndViewedProductsIfNotExists(productId, memberId.toString());
    }

    @Transactional
    public void increaseViews(String clientIP, Long productId) {
        increaseViewsAndViewedProductsIfNotExists(productId, clientIP);
    }

    private void increaseViewsAndViewedProductsIfNotExists(Long productId, String memberId) {
        if (!isViewedProductExists(memberId, productId)) {
            statRedisRepository.increaseViews(productId);
            statRedisRepository.saveMemberViewedProducts(memberId, productId);
        }
    }

    private Boolean isViewedProductExists(String memberId, Long productId) {
        List<String> memberViewedProducts = statRedisRepository.findMemberViewedProducts(memberId);
        return memberViewedProducts != null && memberViewedProducts.contains(productId);
    }

    public void addOrResetWishes(Long memberId, Long productId) {
        if (isWishedProductExists(memberId, productId)) {
            resetWishes(productId, memberId);
            return;
        }
        addWishes(productId, memberId);
    }

    public Boolean isWishedProductExists(Long memberId, Long productId) {
        List<Long> memberViewedProducts = statRedisRepository.findMemberWishedProducts(memberId);
        return memberViewedProducts != null && memberViewedProducts.contains(productId);
    }

    private void addWishes(Long productId, Long memberId) {
        statRedisRepository.increaseWishes(productId);
        statRedisRepository.saveMemberWishedProducts(memberId, productId);
    }

    private void resetWishes(Long memberId, Long productId) {
        statRedisRepository.decreaseWishes(productId);
        statRedisRepository.deleteMemberWishedProducts(memberId, productId);
    }
}
