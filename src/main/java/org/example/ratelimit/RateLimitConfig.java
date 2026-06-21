package org.example.ratelimit;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.*;

@Configuration
@RequiredArgsConstructor
public class RateLimitConfig {

    private final RateLimitFilter rateLimitFilter;

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitRegistration() {

        FilterRegistrationBean<RateLimitFilter> bean =
                new FilterRegistrationBean<>();

        bean.setFilter(rateLimitFilter);

        bean.addUrlPatterns("/api/*");

        bean.setOrder(1);

        return bean;
    }

}