package kr.codesquad.secondhand.api.member.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kr.codesquad.secondhand.api.address.domain.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    private boolean isLastVisited;

    public MemberAddress(Member member, Address address, boolean isLastVisited) {
        this.member = member;
        this.address = address;
        this.isLastVisited = isLastVisited;
    }

    public static List<MemberAddress> of(Member member, List<Address> addresses) {
        List<MemberAddress> memberAddresses = new ArrayList<>();

        for (int i = 0; i < addresses.size(); i++) {
            Address address = addresses.get(i);
            boolean isLastVisited = i == addresses.size() - 1;
            memberAddresses.add(new MemberAddress(member, address, isLastVisited));
        }

        return memberAddresses;
    }

    public void updateLastVisited(boolean isLastVisited) {
        this.isLastVisited = isLastVisited;
    }
}

