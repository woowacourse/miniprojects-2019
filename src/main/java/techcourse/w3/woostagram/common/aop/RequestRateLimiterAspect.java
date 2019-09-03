package techcourse.w3.woostagram.common.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import techcourse.w3.woostagram.article.exception.RequestTooFastException;
import techcourse.w3.woostagram.common.support.UserRateLimiter;

@Aspect
@Component
public class RequestRateLimiterAspect {
    private final UserRateLimiter userRateLimiter;

    public RequestRateLimiterAspect(UserRateLimiter userRateLimiter) {
        this.userRateLimiter = userRateLimiter;
    }

    @Pointcut("execution(public * techcourse.w3.woostagram.*.controller.*.create(..)) && args(.., email)")
    public void createRequests(String email) {}

    @Pointcut("execution(public * techcourse.w3.woostagram.*.controller.*.update(..)) && args(.., email)")
    public void updateRequests(String email) {}

    @Before(value = "createRequests(email) || updateRequests(email)", argNames = "email")
    public void acquire(String email) {
        if (!userRateLimiter.get(email).tryAcquire()) {
            throw new RequestTooFastException();
        }
    }
}
