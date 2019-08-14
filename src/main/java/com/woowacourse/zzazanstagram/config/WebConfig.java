package com.woowacourse.zzazanstagram.config;

import com.woowacourse.zzazanstagram.web.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/signup")
                .excludePathPatterns("/members")
                .excludePathPatterns("/login");
    }
}
