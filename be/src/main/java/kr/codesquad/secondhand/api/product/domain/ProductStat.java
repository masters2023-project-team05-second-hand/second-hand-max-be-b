package kr.codesquad.secondhand.api.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductStat {

    @Id
    @Column(name = "product_id")
    private Long id;

    private Integer viewCount;
    private Integer wishCount;

    @Builder
    private ProductStat(Long id, Integer viewCount, Integer wishCount) {
        this.id = id;
        this.viewCount = viewCount;
        this.wishCount = wishCount;
    }
}
