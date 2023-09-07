package kr.codesquad.secondhand.api.product.repository;

import java.util.List;
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
    private static final String VIEWS_KEY = "VIEWS";
    private static final String WISHES_KEY = "WISHES";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveNewProductStats(String key) {
        redisTemplate.opsForHash().put(key, VIEWS_KEY, DEFAULT_COUNT);
        redisTemplate.opsForHash().put(key, WISHES_KEY, DEFAULT_COUNT);
    }

    public List<Object> findProductStats(String productId) {
        return redisTemplate.opsForHash().multiGet(productId, List.of(VIEWS_KEY, WISHES_KEY));
    }

    public void increaseViews(String productKey) {
        redisTemplate.opsForHash().increment(productKey, VIEWS_KEY, INCREMENT_COUNT);
    }

    public List<String> findMemberViewedProducts(String memberViewedKey) {
        return redisTemplate.opsForList().range(memberViewedKey, START, END);
    }

    public void saveMemberViewedProducts(String memberId, String productId) {
        redisTemplate.opsForList().rightPush(memberId, productId);
    }

    public void increaseWishes(String productKey) {
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, INCREMENT_COUNT);
    }

    public List<String> findMemberWishedProducts(String memberWishedProductsKey) {
        return redisTemplate.opsForList().range(memberWishedProductsKey, START, END);
    }

    public void saveMemberWishedProducts(String memberWishedProductsKey, String productId) {
        redisTemplate.opsForList().rightPush(memberWishedProductsKey, productId);
    }

    public void deleteMemberWishedProducts(String memberWishedProductsKey, String productId) {
        redisTemplate.opsForList().remove(memberWishedProductsKey, Long.parseLong(DEFAULT_COUNT), productId);
    }

    public void decreaseWishes(String productKey) {
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, DECREMENT_COUNT);
    }
}
