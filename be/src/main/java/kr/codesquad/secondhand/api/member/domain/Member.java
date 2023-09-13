package kr.codesquad.secondhand.api.member.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import kr.codesquad.secondhand.api.oauth.domain.OAuthAttributes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdTime;

    private String email;
    private String nickname;
    private String profileImgUrl;


    private Long signInTypeId;

    @Builder
    public Member(OAuthAttributes oAuthAttributes, String email, String nickname, String profileImgUrl) {
        this.signInTypeId = oAuthAttributes.getId();
        this.email = email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateProfileImgUrl(String newProfileImgUrl) {
        this.profileImgUrl = newProfileImgUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
