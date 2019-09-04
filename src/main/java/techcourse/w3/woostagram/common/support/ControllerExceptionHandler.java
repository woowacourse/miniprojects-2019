package techcourse.w3.woostagram.common.support;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import techcourse.w3.woostagram.common.exception.WoostagramException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@ControllerAdvice(annotations = Controller.class)
@Order(2)
public class ControllerExceptionHandler {
    private static final String EXCEPTION_MESSAGE = "예기치 않은 오류가 발생했습니다.";

    @ExceptionHandler(WoostagramException.class)
    @Order(1)
    public String handleWoostagramException(Exception error, HttpServletRequest servletRequest, RedirectAttributes redirectAttributes) {
        return handleException(servletRequest, redirectAttributes, error.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @Order(2)
    public String handleUnexpectedException(HttpServletRequest servletRequest, RedirectAttributes redirectAttributes) {
        return handleException(servletRequest, redirectAttributes, EXCEPTION_MESSAGE);
    }

    private static String handleException(HttpServletRequest servletRequest, RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute("error", true);
        redirectAttributes.addFlashAttribute("message", message);

        String url = Optional.of(servletRequest.getHeader(HttpHeaders.REFERER)).orElse("/");

        return "redirect:" + url;
    }
}