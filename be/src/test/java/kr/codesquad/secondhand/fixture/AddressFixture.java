package kr.codesquad.secondhand.fixture;

import kr.codesquad.secondhand.api.address.domain.Address;

public enum AddressFixture {

    YUKSAM_1DONG(1L, "서울특별시 강남구 역삼1동"),
    YUKSAM_2DONG(2L, "서울특별시 강남구 역삼2동"),
    SAJIK(1111053000L, "서울특별시 종로구 사직동"),
    SAMCHEONG(1111054000L, "서울특별시 종로구 삼청동"),
    BUAM(1111055000L, "서울특별시 종로구 부암동"),
    PYEONGCHANG(1111056000L, "서울특별시 종로구 평창동");

    private final Long id;
    private final String name;

    AddressFixture(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Address toAddress() {
        return new Address(id, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
