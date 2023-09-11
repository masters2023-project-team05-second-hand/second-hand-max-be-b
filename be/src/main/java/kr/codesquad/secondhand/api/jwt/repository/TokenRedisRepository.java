package kr.codesquad.secondhand.api.jwt.repository;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepository {

    private static final String ACCESS_TOKEN_KEY = "accessToken";

    private final RedisTemplate<String, String> template;

    public void addAccessTokenToBlackList(String token, long minutes) {
        template.opsForValue().set(token, ACCESS_TOKEN_KEY, minutes, TimeUnit.MINUTES);
    }

    public boolean isAccessTokenInBlackList(String accessToken) {
        return Boolean.TRUE.equals(template.hasKey(accessToken));
    }
}
