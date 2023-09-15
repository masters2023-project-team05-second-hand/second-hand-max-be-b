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
    private static final String VIEWS_KEY = "::views";
    private static final String WISHES_KEY = "::wishes";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveNewProductStats(Long productId) {
        String productKey = productId.toString();
        redisTemplate.opsForHash().put(productKey, VIEWS_KEY, DEFAULT_COUNT);
        redisTemplate.opsForHash().put(productKey, WISHES_KEY, DEFAULT_COUNT);
    }

    public ProductStats findProductStats(Long productId) {
        String productKey = productId.toString();
        List<Object> stats = redisTemplate.opsForHash().multiGet(productKey, List.of(VIEWS_KEY, WISHES_KEY));
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
        String productKey = productId.toString();
        redisTemplate.opsForHash().increment(productKey, VIEWS_KEY, INCREMENT_COUNT);
    }

    public List<String> findMemberViewedProducts(String memberKey) {
        String memberViewedKey = memberKey.toString() + VIEWS_KEY;
        return redisTemplate.opsForList().range(memberViewedKey, START, END);
    }

    public void saveMemberViewedProducts(String memberKey, Long productId) {
        String memberViewedKey = memberKey + VIEWS_KEY;
        String productKey = productId.toString();
        redisTemplate.opsForList().rightPush(memberViewedKey, productKey);
    }

    public void increaseWishes(Long productId) {
        String productKey = productId.toString();
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, INCREMENT_COUNT);
    }

    public List<Long> findMemberWishedProducts(Long memberId) {
        String memberWishedProductsKey = memberId.toString() + WISHES_KEY;
        List<String> result = redisTemplate.opsForList().range(memberWishedProductsKey, START, END);
        if (result == null) {
            return new ArrayList<>();
        }
        return result.stream()
                .map(Long::valueOf)
                .collect(Collectors.toUnmodifiableList());
    }

    public void saveMemberWishedProducts(Long memberId, Long productId) {
        String memberWishedProductsKey = memberId.toString() + WISHES_KEY;
        String productKey = productId.toString();
        redisTemplate.opsForList().rightPush(memberWishedProductsKey, productKey);
    }

    public void deleteMemberWishedProducts(Long memberId, Long productId) {
        String memberWishedProductsKey = memberId.toString() + WISHES_KEY;
        String productKey = productId.toString();
        redisTemplate.opsForList().remove(memberWishedProductsKey, Long.parseLong(DEFAULT_COUNT), productKey);
    }

    public void decreaseWishes(Long productId) {
        String productKey = productId.toString();
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, DECREMENT_COUNT);
    }
}
