package techcourse.w3.woostagram.common.support;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import techcourse.w3.woostagram.user.interceptor.LoggedInInterceptor;

@Configuration
public class LoggedInConfig implements WebMvcConfigurer {
    private final LoggedInInterceptor loggedInInterceptor;

    public LoggedInConfig(LoggedInInterceptor loggedInInterceptor) {
        this.loggedInInterceptor = loggedInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggedInInterceptor)
                .addPathPatterns("/users/signup/form")
                .addPathPatterns("/users/signup")
                .addPathPatterns("/users/login")
                .addPathPatterns("/users/login/form");
    }

}
