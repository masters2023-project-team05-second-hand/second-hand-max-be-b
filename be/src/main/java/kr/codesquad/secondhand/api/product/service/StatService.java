package kr.codesquad.secondhand.api.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import kr.codesquad.secondhand.api.product.repository.StatRedisRepository;
import kr.codesquad.secondhand.global.exception.CustomRuntimeException;
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

    public ProductStat findProductStat(Long productId) {
        return statRedisRepository.findProductStat(productId);
    }

    public Map<Long, ProductStats> findProductsStats(List<Product> products) {
        return statRedisRepository.findProductsStats(products);
    }

    public List<Long> findWishlistByMemberId(Long memberId) {
        List<Long> wishlist = statRedisRepository.findMemberWishedProducts(memberId);
        return wishlist;
    }

    public MemberStatLog findMemberStatLog(Long memberId) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Long> viewedProducts = statRedisRepository.findMemberViewedProducts(memberId.toString());
        List<Long> wishedProducts = statRedisRepository.findMemberWishedProducts(memberId);
        try {
            return MemberStatLog.builder()
                    .id(memberId)
                    .viewedProductIds(objectMapper.writeValueAsString(viewedProducts))
                    .wishedProductIds(objectMapper.writeValueAsString(wishedProducts))
                    .build();
        } catch (JsonProcessingException e) {
            throw new CustomRuntimeException("redis 멤버 스탯 Json 직렬화 에러입니다.");
        }
    }

    public Map<String, Set<Long>> getKeys() {
        return statRedisRepository.getKeys();
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
        List<Long> memberViewedProducts = statRedisRepository.findMemberViewedProducts(memberId);
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

    public void flushAllRedis() {
        statRedisRepository.flushAllRedis();
    }
}
