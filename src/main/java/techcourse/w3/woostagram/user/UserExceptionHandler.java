package techcourse.w3.woostagram.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techcourse.w3.woostagram.user.exception.LoginException;
import techcourse.w3.woostagram.user.exception.UserCreateException;
import techcourse.w3.woostagram.user.exception.UserUpdateException;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(LoginException.class)
    public String loginException(LoginException error, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", error.getMessage());
        log.debug("errorMessage: {}", error.getMessage());
        return "redirect:/users/login/form";
    }

    @ExceptionHandler(UserCreateException.class)
    public String userCreateException(UserCreateException error, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", error.getMessage());
        log.debug("errorMessage: {}", error.getMessage());
        return "redirect:/users/signup/form";
    }

    @ExceptionHandler(UserUpdateException.class)
    public String userUpdateException(UserUpdateException error, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", error.getMessage());
        log.debug("errorMessage: {}", error.getMessage());
        return "redirect:/users/mypage-edit/form";
    }

    @ExceptionHandler(BindException.class)
    public String bindException(BindException error, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", "userName은 빈칸을 허용하지 않는 항목입니다.");
        log.debug("e.getBindingResult : {}", error.getBindingResult());
        log.debug("e.getObjectName: {}", error.getObjectName());
        return "redirect:/users/mypage-edit/form";
    }
}
