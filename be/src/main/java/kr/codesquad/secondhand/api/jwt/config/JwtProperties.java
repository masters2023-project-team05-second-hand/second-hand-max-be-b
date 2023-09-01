package kr.codesquad.secondhand.api.jwt.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String secretKey;
    private Long accessTokenExpirationTime;
    private Long refreshTokenExpirationTime;

}
