package com.woowacourse.dsgram.web.config;

import com.woowacourse.dsgram.web.argumentresolver.UserSessionArgumentResolver;
import com.woowacourse.dsgram.web.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("#{'${INTERCEPTOR_PATH}'.split(',')}")
    private String[] interceptorPaths;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(interceptorPaths[0])
                .excludePathPatterns(interceptorPaths[1])
                .excludePathPatterns(interceptorPaths[2])
                .excludePathPatterns(interceptorPaths[3])
                .excludePathPatterns(interceptorPaths[4])
                .excludePathPatterns(interceptorPaths[5]);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new UserSessionArgumentResolver());
    }
}
