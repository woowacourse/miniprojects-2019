package techcourse.w3.woostagram.common.support;


import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import techcourse.w3.woostagram.common.exception.WoostagramExeception;

@ControllerAdvice(annotations = RestController.class)
@Order(1)
public class RestControllerExceptionHandler {
    private static final String EXCEPTION_MESSAGE = "예기치 않은 오류가 발생했습니다.";

    @ExceptionHandler(WoostagramExeception.class)
    @Order(1)
    public ResponseEntity handleWoostagramExeception(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @Order(2)
    public ResponseEntity handleUnexpectedException(Exception e) {
        return ResponseEntity.badRequest().body(EXCEPTION_MESSAGE);
    }
}
