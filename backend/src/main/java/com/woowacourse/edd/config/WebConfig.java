package com.woowacourse.edd.config;

import com.woowacourse.edd.interceptor.NoSignInInterceptor;
import com.woowacourse.edd.interceptor.SignInInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInInterceptor())
            .addPathPatterns("/v1/users/**")
            .addPathPatterns("/v1/login");

        registry.addInterceptor(new NoSignInInterceptor())
            .addPathPatterns("/v1/videos/**")
            .addPathPatterns("/v1/users/**")
            .addPathPatterns("/v1/logout");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
