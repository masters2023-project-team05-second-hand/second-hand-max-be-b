package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.member.domain.MemberStatLogRedis;
import kr.codesquad.secondhand.api.member.service.MemberStatLogRedisService;
import kr.codesquad.secondhand.api.member.service.MemberStatLogService;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatsSchedulerFacadeService {

    private final ProductStatService productStatService;
    private final MemberStatLogService memberStatLogService;
    private final MemberStatLogRedisService memberStatLogRedisService;

    @Transactional
    @Scheduled(cron = "0 0/1 * * * ?")
    public void backUpProductStat() {
        Set<Long> productKeys = productStatService.getProductKeys();

        List<ProductStat> productStats = productKeys.stream()
                .map(productStatService::findProductStat)
                .collect(Collectors.toUnmodifiableList());
        List<MemberStatLogRedis> memberStatLogRedis = memberStatLogRedisService.findAll();
        List<MemberStatLog> memberStatLogs = memberStatLogRedis.stream()
                .map(MemberStatLog::from)
                .collect(Collectors.toUnmodifiableList());
        productStatService.flushAllRedis();
        productStatService.saveAll(productStats);
        memberStatLogService.saveAll(memberStatLogs);
    }
}
