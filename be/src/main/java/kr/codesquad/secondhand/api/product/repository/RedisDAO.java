package kr.codesquad.secondhand.api.product.repository;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisDAO {

    private final RedisTemplate<String, String> redisTemplate;
    private final Long INCREMENT_SIZE = 1L;
    private final Long DECREMENT_SIZE = -1L;
    private final String VIEWS_KEY = "views";
    private final String WISHES_KEY = "WISHES";

    public void saveNewProductStats(String key) {
        redisTemplate.opsForHash().put(key, VIEWS_KEY, "0");
        redisTemplate.opsForHash().put(key, WISHES_KEY, "0");
    }

    public List<Object> readProductStats(String productId) {
        return redisTemplate.opsForHash().multiGet(productId, Arrays.asList(VIEWS_KEY, WISHES_KEY));
    }

    public void increaseViews(String memberViewedProductsKey, String productKey) {
        redisTemplate.opsForHash().increment(productKey, VIEWS_KEY, INCREMENT_SIZE);
    }

    public List<String> readMemberViewedProductsList(String memberViewedKey) {
        List<String> memberViewedProductsList = redisTemplate.opsForList().range(memberViewedKey, 0, -1);
        return memberViewedProductsList;
    }

    public void saveMemberViewedProducts(String memberId, String productId) {
        redisTemplate.opsForList().rightPush(memberId, productId);
    }

    public void increaseWishes(String memberWishedProductsKey, String productKey) {
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, INCREMENT_SIZE);
        saveMemberViewedProducts(memberWishedProductsKey, productKey);
    }

    public List<String> readMemberWishedProductsList(String memberWishedProductsKey) {
        List<String> memberViewedProductsList = redisTemplate.opsForList().range(memberWishedProductsKey, 0, -1);
        return memberViewedProductsList;
    }

    public void saveMemberWishedProducts(String memberWishedProductsKey, String productId) {
        redisTemplate.opsForList().rightPush(memberWishedProductsKey, productId);
    }

    public void deleteMemberWishedProducts(String memberWishedProductsKey, String productId) {
        redisTemplate.opsForList().remove(memberWishedProductsKey, 0, productId);
    }

    public void decreaseWishes(String memberWishedProductsKey, String productKey) {
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, DECREMENT_SIZE);
    }
}
