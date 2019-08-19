package techcourse.w3.woostagram.user.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import techcourse.w3.woostagram.user.interceptor.LogonInterceptor;
import techcourse.w3.woostagram.user.interceptor.LoginInterceptor;

@Configuration
public class LoginConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;
    private final LogonInterceptor logonInterceptor;

    public LoginConfig(LoginInterceptor loginInterceptor, LogonInterceptor logonInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.logonInterceptor = logonInterceptor;
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

        registry.addInterceptor(logonInterceptor)
                .addPathPatterns("/users/signup/form")
                .addPathPatterns("/users/signup")
                .addPathPatterns("/users/login")
                .addPathPatterns("/users/login/form");
    }
}
