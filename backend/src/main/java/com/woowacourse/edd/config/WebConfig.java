package com.woowacourse.edd.config;

import com.woowacourse.edd.interceptor.NoSignInRequiredInterceptor;
import com.woowacourse.edd.interceptor.SignInRequiredInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        needNotSignedIn(registry, "/v1/users", "POST");
        needNotSignedIn(registry, "/v1/login", "POST");

        needSignedIn(registry, "/v1/videos/**", "PUT", "DELETE", "POST");
        needSignedIn(registry, "/v1/users/?**", "PUT", "DELETE");
        needSignedIn(registry, "/v1/logout", "POST");
        needSignedIn(registry, "/v1/login/users", "GET");
        needSignedIn(registry, "/v1/users/**/subscribe", "POST", "DELETE");
    }

    private void needSignedIn(InterceptorRegistry registry, String pathPattern, String... allowedMethods) {
        registry.addInterceptor(new NoSignInRequiredInterceptor(allowedMethods))
            .addPathPatterns(pathPattern);
    }

    private void needNotSignedIn(InterceptorRegistry registry, String pathPattern, String... allowedMethods) {
        registry.addInterceptor(new SignInRequiredInterceptor(allowedMethods))
            .addPathPatterns(pathPattern);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
