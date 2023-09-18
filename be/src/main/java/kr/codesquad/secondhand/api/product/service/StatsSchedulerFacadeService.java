package kr.codesquad.secondhand.api.product.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import kr.codesquad.secondhand.api.member.domain.MemberStatLog;
import kr.codesquad.secondhand.api.member.service.MemberStatLogService;
import kr.codesquad.secondhand.api.product.domain.ProductStat;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatsSchedulerFacadeService {

    private final StatService statService;
    private final ProductStatService productStatService;
    private final MemberStatLogService memberStatLogService;

    @Transactional
    @Scheduled(cron = "0 0/3 * * * ?")
    public void backUpProductStat() {
        Map<String, Set<Long>> keys = statService.getKeys();

        Set<Long> productKeys = keys.get("product");
        List<ProductStat> productStats = productKeys.stream()
                .map(statService::findProductStat)
                .collect(Collectors.toUnmodifiableList());

        Set<Long> memberKeys = keys.get("member");
        List<MemberStatLog> memberStatLogs = memberKeys.stream()
                .map(statService::findMemberStatLog)
                .collect(Collectors.toUnmodifiableList());
        statService.flushAllRedis();
        productStatService.saveAll(productStats);
        memberStatLogService.saveAll(memberStatLogs);
    }
}
