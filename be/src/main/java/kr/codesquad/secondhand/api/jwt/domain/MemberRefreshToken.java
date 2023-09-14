package kr.codesquad.secondhand.api.jwt.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "token")
public class MemberRefreshToken {

    @Id
    private Long memberId;

    private String refreshToken;

    public MemberRefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public boolean matches(String refreshToken) {
        return Objects.equals(this.refreshToken, refreshToken);
    }
}
