package techcourse.w3.woostagram.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Can not find User.")
public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
