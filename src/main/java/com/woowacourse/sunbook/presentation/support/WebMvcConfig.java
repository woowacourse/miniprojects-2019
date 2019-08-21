package com.woowacourse.sunbook.presentation.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final HandlerInterceptorAdapter interceptor;
    private final HandlerMethodArgumentResolver userSessionArgumentResolver;

    public WebMvcConfig(HandlerInterceptorAdapter interceptor, HandlerMethodArgumentResolver userSessionArgumentResolver) {
        this.interceptor = interceptor;
        this.userSessionArgumentResolver = userSessionArgumentResolver;
    }

    // TODO :  데모 전 설정 바꾸기
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = Arrays.asList("/", "/test", "/upload", "/api/signin", "/api/signup", "/js/**", "/css/**", "/images/**");
        registry.addInterceptor(interceptor)
                .excludePathPatterns(patterns)
                .excludePathPatterns("/**");
//                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userSessionArgumentResolver);
    }
}