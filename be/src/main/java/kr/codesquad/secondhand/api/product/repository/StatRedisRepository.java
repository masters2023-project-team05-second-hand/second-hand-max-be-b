package kr.codesquad.secondhand.api.product.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.Product;
import kr.codesquad.secondhand.api.product.domain.ProductStats;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatRedisRepository {

    private static final Long INCREMENT_COUNT = 1L;
    private static final Long DECREMENT_COUNT = -1L;
    private static final Integer START = 0;
    private static final Integer END = -1;
    private static final String DEFAULT_COUNT = "0";
    private static final String VIEWS_FIELD = "::views";
    private static final String WISHES_FIELD = "::wishes";
    private static final String VIEWS_KEY = "views::";
    private static final String WISHES_KEY = "wishes::";
    private static final String PRODUCT_KEY = "product::";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveNewProductStats(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().put(productKey, VIEWS_FIELD, DEFAULT_COUNT);
        redisTemplate.opsForHash().put(productKey, WISHES_FIELD, DEFAULT_COUNT);
    }

    public ProductStats findProductStats(Long productId) {
        String productKey = generateProductKey(productId);
        List<Object> stats = redisTemplate.opsForHash().multiGet(productKey, List.of(VIEWS_FIELD, WISHES_FIELD));
        return ProductStats.from(stats);
    }

    public Map<Long, ProductStats> findProductsStats(List<Product> products) {
        return products.stream()
                .collect(Collectors.toUnmodifiableMap(
                        product -> product.getId(),
                        product -> findProductStats(product.getId()))
                );
    }

    public void increaseViews(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, VIEWS_FIELD, INCREMENT_COUNT);
    }

    public List<Long> findMemberViewedProducts(String memberKey) {
        String memberViewedKey = generateMemberViewedProductsKey(memberKey);
        List<String> result = redisTemplate.opsForList().range(memberViewedKey, START, END);
        if (result == null) {
            return new ArrayList<>();
        }
        return result.stream()
                .map(Long::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveMemberViewedProducts(String memberKey, Long productId) {
        String memberViewedKey = generateMemberViewedProductsKey(memberKey);
        String productValue = productId.toString();
        redisTemplate.opsForList().rightPush(memberViewedKey, productValue);
    }

    public void increaseWishes(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, WISHES_FIELD, INCREMENT_COUNT);
    }

    public List<Long> findMemberWishedProducts(Long memberId) {
        String memberWishedProductsKey = generateMemberWishedProductsKey(memberId);
        List<String> result = redisTemplate.opsForList().range(memberWishedProductsKey, START, END);
        if (result == null) {
            return new ArrayList<>();
        }
        return result.stream()
                .map(Long::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveMemberWishedProducts(Long memberId, Long productId) {
        String memberWishedProductsKey = generateMemberWishedProductsKey(memberId);
        String productValue = productId.toString();
        redisTemplate.opsForList().rightPush(memberWishedProductsKey, productValue);
    }

    public void deleteMemberWishedProducts(Long memberId, Long productId) {
        String memberWishedProductsKey = generateMemberWishedProductsKey(memberId);
        String productValue = productId.toString();
        redisTemplate.opsForList().remove(memberWishedProductsKey, Long.parseLong(DEFAULT_COUNT), productValue);
    }

    public void decreaseWishes(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, WISHES_FIELD, DECREMENT_COUNT);
    }

    private String generateProductKey(Long productId) {
        return PRODUCT_KEY + productId;
    }

    private String generateMemberViewedProductsKey(String memberId) {
        return VIEWS_KEY + memberId;
    }

    private String generateMemberWishedProductsKey(Long memberId) {
        return WISHES_KEY + memberId.toString();
    }
}
