package kr.codesquad.secondhand.global.util;

import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.jwt.exception.TokenNotFoundException;
import org.springframework.http.HttpHeaders;

public class HttpAuthorizationUtils {

    private static final String MEMBER_ID_CLAIMS_KEY = "memberId";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final int BEARER_TOKEN_PREFIX_LENGTH = TOKEN_PREFIX.length() + 1;

    public static void setAttributeFromToken(HttpServletRequest httpServletRequest, Claims claims) {
        httpServletRequest.setAttribute(MEMBER_ID_CLAIMS_KEY, claims.get(MEMBER_ID_CLAIMS_KEY));
    }

    public static String extractAccessToken(HttpServletRequest httpServletRequest) throws TokenNotFoundException {
        String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            return authorizationHeader.substring(BEARER_TOKEN_PREFIX_LENGTH);
        } catch (StringIndexOutOfBoundsException e) {
            throw new TokenNotFoundException();
        }
    }

    public static boolean containsBearerToken(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization != null && authorization.startsWith(TOKEN_PREFIX);
    }

    public static Long extractMemberId(HttpServletRequest httpServletRequest) {
        return Long.valueOf(httpServletRequest.getAttribute(MEMBER_ID_CLAIMS_KEY).toString()) ;
    }
}
