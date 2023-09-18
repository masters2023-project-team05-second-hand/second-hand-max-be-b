package kr.codesquad.secondhand.api.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStatLog {

    @Id
    @Column(name = "member_id")
    private Long id;

    private String viewedProductIds;
    private String wishedProductIds;

    @Builder
    public MemberStatLog(Long id, String viewedProductIds, String wishedProductIds) {
        this.id = id;
        this.viewedProductIds = viewedProductIds;
        this.wishedProductIds = wishedProductIds;
    }
}
