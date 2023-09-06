package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.product.repository.RedisDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisDAO redisDAO;

    public void saveNewProductStats(Long productId) {
        String key = productId.toString();
        redisDAO.saveNewProductStats(key);
    }

    public List<Integer> readProductStats(Long productId) {
        String productKey = productId.toString();
        List<Object> objectStatsList = redisDAO.readProductStats(productKey);
        List<Integer> integerStatsList = objectStatsList.stream()
                .map(value -> Integer.parseInt(value.toString()))
                .collect(Collectors.toUnmodifiableList());
        return integerStatsList;
    }

    public void increaseViews(Long memberId, Long productId) {
        String productKey = productId.toString();
        String memberViewedProductsKey = memberId.toString() + "::views";
        if (!isViewedProduct(memberViewedProductsKey, productKey)) {
            redisDAO.increaseViews(memberViewedProductsKey, productKey);
            redisDAO.saveMemberViewedProducts(memberViewedProductsKey, productKey);
        }
    }

    public void increaseWishes(Long memberId, Long productId) {
        String productKey = productId.toString();
        String memberWishedProductsKey = memberId.toString() + "::wishes";
        if (!isWishedProduct(memberWishedProductsKey, productKey)) {
            redisDAO.increaseWishes(memberWishedProductsKey, productKey);
            redisDAO.saveMemberWishedProducts(memberWishedProductsKey, productKey);
            return;
        }
        //좋아요를 한번 더 누를 경우 좋아요를 누르지 않은 상태로 복귀
        redisDAO.decreaseWishes(memberWishedProductsKey, productKey);
        redisDAO.deleteMemberWishedProducts(memberWishedProductsKey, productKey);
    }

    public Boolean isViewedProduct(String memberViewedProductsKey, String productId) {
        List<String> memberViewedProductsList = redisDAO.readMemberViewedProductsList(memberViewedProductsKey);
        return memberViewedProductsList != null && memberViewedProductsList.contains(productId);
    }

    public Boolean isWishedProduct(String memberWishedProductsKey, String productId) {
        List<String> memberViewedProductsList = redisDAO.readMemberWishedProductsList(memberWishedProductsKey);
        return memberViewedProductsList != null && memberViewedProductsList.contains(productId);
    }
}
