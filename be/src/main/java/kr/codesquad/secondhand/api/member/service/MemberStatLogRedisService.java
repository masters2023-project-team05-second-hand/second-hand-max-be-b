package kr.codesquad.secondhand.api.member.service;

import java.util.List;
import java.util.Optional;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.member.domain.MemberStatLogRedis;
import kr.codesquad.secondhand.api.member.repository.MemberStatLogRedisRepository;
import kr.codesquad.secondhand.global.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberStatLogRedisService {

    private final MemberStatLogRedisRepository memberStatLogRedisRepository;


    public void cacheMemberStatLogIfExists(Long memberId, Optional<MemberStatLog> memberStatLog) {
        if (memberStatLog.isEmpty()) {
            memberStatLogRedisRepository.save(new MemberStatLogRedis(memberId));
            return;
        }
        MemberStatLog memberStat = memberStatLog.get();
        memberStatLogRedisRepository.save(MemberStatLogRedis.builder()
                .id(memberStat.getId())
                .viewedProductIds(memberStat.getViewedProductIds())
                .wishedProductIds(memberStat.getWishedProductIds())
                .build());
    }

    public MemberStatLogRedis findById(Long memberId) {
        return memberStatLogRedisRepository.findById(memberId)
                .orElseThrow(() -> new CustomRuntimeException("멤버 스탯 로그 에러"));
    }

    public List<MemberStatLogRedis> findAll(){
        return memberStatLogRedisRepository.findAll();
    }

    public List<Long> findWishlistByMemberId(Long memberId) {
        return findById(memberId).getWishedProductList();
    }

    public Boolean existById(Long memberId) {
        return memberStatLogRedisRepository.existsById(memberId);
    }

    public void addViewedProductIds(Long viewedProductId, Long memberId) {
        MemberStatLogRedis memberStatLog = findById(memberId);
        List<Long> viewedProducts = memberStatLog.getViewedProductIdList();
        viewedProducts.add(viewedProductId);
        memberStatLog.setViewedProductIds(viewedProducts);
        memberStatLogRedisRepository.save(memberStatLog);
    }

    public void addWishedProductIds(Long wishedProductId, Long memberId) {
        MemberStatLogRedis memberStatLog = findById(memberId);
        List<Long> wishedProducts = memberStatLog.getWishedProductList();
        wishedProducts.add(wishedProductId);
        memberStatLog.setWishedProductIds(wishedProducts);
        memberStatLogRedisRepository.save(memberStatLog);
    }

    public void removeWishedProductIds(Long wishedProductId, Long memberId) {
        MemberStatLogRedis memberStatLog = findById(memberId);
        List<Long> wishedProducts = memberStatLog.getWishedProductList();
        wishedProducts.remove(wishedProductId);
        memberStatLog.setWishedProductIds(wishedProducts);
        memberStatLogRedisRepository.save(memberStatLog);
    }

    public Boolean containsViewedProductId(Long memberId, Long productId) {
        MemberStatLogRedis memberStatLog = findById(memberId);
        return memberStatLog.getViewedProductIdList().contains(productId);
    }

    public Boolean containsWishedProductId(Long memberId, Long productId) {
        MemberStatLogRedis memberStatLog = findById(memberId);
        return memberStatLog.getWishedProductList().contains(productId);
    }
}
