package kr.codesquad.secondhand.global.util;

import io.jsonwebtoken.Claims;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.jwt.exception.TokenNotFoundException;
import org.springframework.http.HttpHeaders;

public class HttpAuthorizationUtils {

    private static final String MEMBER_ID_CLAIMS_KEY = "memberId";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final int BEARER_TOKEN_PREFIX_LENGTH = TOKEN_PREFIX.length() + 1;
    public static final List<String> IP_CHECK_HEADERS = List.of(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    );

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

    public static boolean isMember(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getAttribute(MEMBER_ID_CLAIMS_KEY) != null;
    }

    public static String getClientIp(HttpServletRequest httpServletRequest) {
        for (String header : IP_CHECK_HEADERS) {
            String ip = httpServletRequest.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return httpServletRequest.getRemoteAddr();
    }

}
