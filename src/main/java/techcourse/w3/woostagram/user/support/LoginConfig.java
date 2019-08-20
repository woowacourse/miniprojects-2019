package techcourse.w3.woostagram.user.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import techcourse.w3.woostagram.user.interceptor.LoggedInInterceptor;
import techcourse.w3.woostagram.user.interceptor.LoginInterceptor;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LoggedInInterceptor loggedInInterceptor;

    public LoginConfig(LoginInterceptor loginInterceptor, LoggedInInterceptor loggedInInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.loggedInInterceptor = loggedInInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/users/signup/form")
                .excludePathPatterns("/users/signup")
                .excludePathPatterns("/users/login")
                .excludePathPatterns("/users/login/form")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/images/**");

        registry.addInterceptor(loggedInInterceptor)
                .addPathPatterns("/users/signup/form")
                .addPathPatterns("/users/signup")
                .addPathPatterns("/users/login")
                .addPathPatterns("/users/login/form");
    }
}
