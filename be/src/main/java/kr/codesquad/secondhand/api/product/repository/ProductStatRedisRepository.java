package kr.codesquad.secondhand.api.product.repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductStatRedisRepository {

    private static final Long INCREMENT_COUNT = 1L;
    private static final Long DECREMENT_COUNT = -1L;
    private static final String DEFAULT_COUNT = "0";
    private static final String VIEWS_KEY = "views::";
    private static final String WISHES_KEY = "wishes::";
    private static final String PRODUCT_KEY = "product::";

    private final RedisTemplate<String, String> redisTemplate;

    public void saveNewProductStats(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().put(productKey, VIEWS_KEY, DEFAULT_COUNT);
        redisTemplate.opsForHash().put(productKey, WISHES_KEY, DEFAULT_COUNT);
    }

    public void saveProductStat(ProductStat productStat) {
        String productKey = generateProductKey(productStat.getId());
        redisTemplate.opsForHash().put(productKey, VIEWS_KEY, productStat.getViewCount().toString());
        redisTemplate.opsForHash().put(productKey, WISHES_KEY, productStat.getWishCount().toString());
    }

    public ProductStat findProductStat(Long productId) {
        String productKey = generateProductKey(productId);
        List<Object> stats = redisTemplate.opsForHash().multiGet(productKey, List.of(VIEWS_KEY, WISHES_KEY));
        Integer viewCount = Integer.parseInt(stats.get(0).toString());
        Integer wishCount = Integer.parseInt(stats.get(1).toString());
        return ProductStat.builder()
                .id(productId)
                .viewCount(viewCount)
                .wishCount(wishCount)
                .build();
    }

    public Set<Long> getProductKeys() {
        return stringCollectionToLongSet(redisTemplate.keys(PRODUCT_KEY + "*"), PRODUCT_KEY);
    }

    public void increaseViews(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, VIEWS_KEY, INCREMENT_COUNT);
    }

    public void increaseWishes(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, INCREMENT_COUNT);
    }

    public void decreaseWishes(Long productId) {
        String productKey = generateProductKey(productId);
        redisTemplate.opsForHash().increment(productKey, WISHES_KEY, DECREMENT_COUNT);
    }

    private String generateProductKey(Long productId) {
        return PRODUCT_KEY + productId;
    }

    private Set<Long> stringCollectionToLongSet(Collection<String> data, String key) {
        if (data == null) {
            return new HashSet<>();
        }
        return data.stream()
                .map(datum -> datum.substring(key.length()))
                .map(Long::valueOf)
                .collect(Collectors.toSet());
    }

    public Boolean hasProductKey(Long productId){
        String productKey = generateProductKey(productId);
        return redisTemplate.hasKey(productKey);
    }

    public void flushAllRedis() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }
}
