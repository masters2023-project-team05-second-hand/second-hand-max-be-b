package kr.codesquad.secondhand.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.codesquad.secondhand.api.jwt.repository.TokenRedisRepository;
import kr.codesquad.secondhand.api.jwt.util.JwtProvider;
import kr.codesquad.secondhand.global.filter.AuthorizationFilter;
import kr.codesquad.secondhand.global.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsFilter());
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(JwtProvider jwtProvider,
                                                                           ObjectMapper objectMapper,
                                                                           TokenRedisRepository tokenRedisRepository) {
        FilterRegistrationBean<AuthorizationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorizationFilter(jwtProvider, objectMapper, tokenRedisRepository));
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}
