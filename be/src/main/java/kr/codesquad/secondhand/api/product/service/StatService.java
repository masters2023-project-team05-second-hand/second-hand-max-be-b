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

    private static final String VIEWS_KEY = "::views";
    private static final String WISHES_KEY = "::wishes";

    private final StatRedisRepository statRedisRepository;

    public void saveNewProductStats(Long productId) {
        String key = productId.toString();
        statRedisRepository.saveNewProductStats(key);
    }

    public ProductStats findProductStats(Long memberId, Long productId) {
        increaseViews(memberId, productId);
        String productKey = productId.toString();
        return statRedisRepository.findProductStats(productKey);
    }

    public Map<Long, ProductStats> findProductsStats(List<Product> products) {
        return statRedisRepository.findProductsStats(products);
    }

    public List<Long> findWishlistByMemberId(Long memberId){
        List<Long> wishlist = statRedisRepository.findMemberWishedProducts(memberId);
        return wishlist;
    }

    @Transactional
    public void increaseViews(Long memberId, Long productId) {
        String productKey = productId.toString();
        String memberViewedProductsKey = memberId.toString() + VIEWS_KEY;
        increaseViewsAndViewedProductsIfNotExists(productKey, memberViewedProductsKey);
    }

    private void increaseViewsAndViewedProductsIfNotExists(String productKey, String memberViewedProductsKey) {
        if (!isViewedProductExists(memberViewedProductsKey, productKey)) {
            statRedisRepository.increaseViews(productKey);
            statRedisRepository.saveMemberViewedProducts(memberViewedProductsKey, productKey);
        }
    }

    private Boolean isViewedProductExists(String memberViewedProductsKey, String productId) {
        List<String> memberViewedProducts = statRedisRepository.findMemberViewedProducts(memberViewedProductsKey);
        return memberViewedProducts != null && memberViewedProducts.contains(productId);
    }

    @Transactional
    public void addOrResetWishes(Long memberId, Long productId) {
        String productKey = productId.toString();
        String memberWishedProductsKey = memberId.toString() + WISHES_KEY;
        if (isWishedProductExists(memberWishedProductsKey, productKey)) {
            resetWishes(productKey, memberWishedProductsKey);
            return;
        }
        addWishes(productKey, memberWishedProductsKey);
    }

    private Boolean isWishedProductExists(String memberWishedProductsKey, String productId) {
        List<String> memberViewedProducts = statRedisRepository.findMemberWishedProducts(memberWishedProductsKey);
        return memberViewedProducts != null && memberViewedProducts.contains(productId);
    }

    private void addWishes(String productKey, String memberWishedProductsKey) {
        statRedisRepository.increaseWishes(productKey);
        statRedisRepository.saveMemberWishedProducts(memberWishedProductsKey, productKey);
    }

    private void resetWishes(String productKey, String memberWishedProductsKey) {
        statRedisRepository.decreaseWishes(productKey);
        statRedisRepository.deleteMemberWishedProducts(memberWishedProductsKey, productKey);
    }
}
