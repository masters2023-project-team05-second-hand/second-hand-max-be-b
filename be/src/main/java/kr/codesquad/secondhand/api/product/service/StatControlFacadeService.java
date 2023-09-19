package kr.codesquad.secondhand.api.product.service;

import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.member.dto.request.WishProductRequest;
import kr.codesquad.secondhand.api.member.service.MemberStatLogRedisService;
import kr.codesquad.secondhand.api.member.service.MemberStatLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatControlFacadeService {

    private final ProductStatService productStatService;
    private final MemberStatLogRedisService memberStatLogRedisService;
    private final MemberStatLogService memberStatLogService;

    public void increaseViews(Long productId, Long memberId) {
        cacheProductStatAndMemberLog(productId, memberId);
        if (!memberStatLogRedisService.containsViewedProductId(memberId, productId)) {
            productStatService.increaseViews(productId);
            memberStatLogRedisService.addViewedProductIds(productId, memberId);
        }
    }

    public void addOrResetWishes(Long memberId, WishProductRequest request) {
        Long productId = request.getProductId();
        cacheProductStatAndMemberLog(productId, memberId);
        if (!memberStatLogRedisService.containsWishedProductId(memberId, productId)) {
            productStatService.increaseWishes(productId);
            memberStatLogRedisService.addWishedProductIds(productId, memberId);
        } else {
            productStatService.decreaseWishes(productId);
            memberStatLogRedisService.removeWishedProductIds(productId, memberId);
        }
    }

    private void cacheProductStatAndMemberLog(Long productId, Long memberId) {
        productStatService.cacheProductStatNotExistsInRedis(productId);
        if (!memberStatLogRedisService.existById(memberId)) {
            Optional<MemberStatLog> memberStatLog = memberStatLogService.findById(memberId);
            memberStatLogRedisService.cacheMemberStatLogIfExists(memberId, memberStatLog);
        }
    }
}
