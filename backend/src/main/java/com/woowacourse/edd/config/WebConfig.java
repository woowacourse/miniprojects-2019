package com.woowacourse.edd.config;

import com.woowacourse.edd.interceptor.MethodNeedNotSignedInInterceptor;
import com.woowacourse.edd.interceptor.MethodNeedSignedInInterceptor;
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
    }

    private void needSignedIn(InterceptorRegistry registry, String pathPattern, String... allowedMethods) {
        registry.addInterceptor(new MethodNeedSignedInInterceptor(allowedMethods))
            .addPathPatterns(pathPattern);
    }

    private void needNotSignedIn(InterceptorRegistry registry, String pathPattern, String... allowedMethods) {
        registry.addInterceptor(new MethodNeedNotSignedInInterceptor(allowedMethods))
            .addPathPatterns(pathPattern);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}
