package kr.codesquad.secondhand.api.jwt.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "token")
public class MemberRefreshToken {

    @Id
    private Long memberId;

    private String refreshToken;

    public boolean matches(String refreshToken) {
        return Objects.equals(this.refreshToken, refreshToken);
    }
}
