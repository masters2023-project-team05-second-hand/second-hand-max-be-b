package kr.codesquad.secondhand.api.product.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductStats {

    private static final Integer VIEW_COUNT_INDEX = 0;
    private static final Integer WISH_COUNT_INDEX = 1;

    private final Integer viewCount;
    private final Integer wishCount;
    private Integer chatCount;

    private ProductStats(Integer viewCount, Integer wishCount) {
        this.viewCount = viewCount;
        this.wishCount = wishCount;
    }

    public static ProductStats from(List<Object> stats){
        Integer viewCount = Integer.parseInt(stats.get(VIEW_COUNT_INDEX).toString());
        Integer wishCount = Integer.parseInt(stats.get(WISH_COUNT_INDEX).toString());
        return new ProductStats(viewCount, wishCount);
    }

    //  TODO Redis의 스탯과 채팅수를 합치기 위해 임시로 setter로 구현, 개선 필요
    public void setChatCount(Integer chatCount) {
        this.chatCount = chatCount;
    }
}

