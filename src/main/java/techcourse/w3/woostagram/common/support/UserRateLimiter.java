package techcourse.w3.woostagram.common.support;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRateLimiter {
    private Map<String, RateLimiter> limiters = new HashMap<>();

    public void put(String userEmail) {
        limiters.put(userEmail, RateLimiter.create(0.5));
    }

    public RateLimiter get(String userEmail) {
        return limiters.get(userEmail);
    }

    public void remove(String userEmail) {
        limiters.remove(userEmail);
    }
}
