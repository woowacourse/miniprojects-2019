package techcourse.w3.woostagram.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import techcourse.w3.woostagram.article.exception.FileSaveFailException;
import techcourse.w3.woostagram.article.exception.InvalidExtensionException;
import techcourse.w3.woostagram.user.dto.UserErrorDto;
import techcourse.w3.woostagram.user.exception.UserNotFoundException;
import techcourse.w3.woostagram.user.exception.UserProfileException;

@Slf4j
@ResponseBody
@ControllerAdvice
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class UserRestExceptionHandler {
    @ExceptionHandler(UserProfileException.class)
    public UserErrorDto handleUserProfileException(UserProfileException error) {
        log.debug("errorMessage: {}", error.getMessage());
        UserErrorDto userErrorDto = new UserErrorDto();
        userErrorDto.setErrorMessage(error.getMessage());
        userErrorDto.setErrorCode("not selected file");
        return userErrorDto;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public UserErrorDto handleUserNotFoundException(UserNotFoundException error) {
        log.debug("errorMessage: {}", error.getMessage());
        UserErrorDto userErrorDto = new UserErrorDto();
        userErrorDto.setErrorMessage(error.getMessage());
        userErrorDto.setErrorCode("not exist user");
        return userErrorDto;
    }

    @ExceptionHandler(InvalidExtensionException.class)
    public UserErrorDto handleInvalidExtensionException(InvalidExtensionException error) {
        log.debug("errorMessage: {}", error.getMessage());
        UserErrorDto userErrorDto = new UserErrorDto();
        userErrorDto.setErrorMessage(error.getMessage());
        userErrorDto.setErrorCode("not support file extension");
        return userErrorDto;
    }

    @ExceptionHandler(FileSaveFailException.class)
    public UserErrorDto handleFileSaveFailException(FileSaveFailException error) {
        log.debug("errorMessage: {}", error.getMessage());
        UserErrorDto userErrorDto = new UserErrorDto();
        userErrorDto.setErrorMessage(error.getMessage());
        userErrorDto.setErrorCode("fail uploading image file");
        return userErrorDto;
    }
}
