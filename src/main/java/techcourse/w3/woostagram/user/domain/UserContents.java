package techcourse.w3.woostagram.user.domain;

import lombok.*;
import org.thymeleaf.util.StringUtils;
import techcourse.w3.woostagram.user.exception.InvalidUserContentsException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class UserContents {
    private static final String USER_NAME_REGEX = "[A-Za-z0-9_.]+";
    public static final String BLANK_ERROR_MESSAGE = "userName은 빈칸을 허용하지 않는 항목입니다.";
    public static final String PATTERN_ERROR_MESSAGE = "사용자 이름에는 문자, 숫자, 밑줄 및 마침표만 사용할 수 있습니다.";
    public static final String LENGTH_ERROR_MESSAGE = "이름을 30자 미만으로 입력하세요.";
    private static final int USER_NAME_MAX_LENGTH = 30;
    private static final int USER_NAME_MIN_LENGTH = 1;

    private String name;

    @Column(nullable = false, unique = true)
    private String userName;

    @Lob
    private String contents;

    @Builder
    public UserContents(String userName, String name, String contents) {
        this.userName = validateUserName(userName);
        this.name = name;
        this.contents = contents;
    }

    private String validateUserName(String userName) {
        checkBlank(userName);
        checkPattern(userName);
        checkLength(userName);
        return userName;
    }

    private void checkBlank(String userName) {
        if (StringUtils.isEmpty(userName)) {
            throw new InvalidUserContentsException(BLANK_ERROR_MESSAGE);
        }
    }

    private void checkPattern(String userName) {
        if (!userName.matches(USER_NAME_REGEX)) {
            throw new InvalidUserContentsException(PATTERN_ERROR_MESSAGE);
        }
    }

    private void checkLength(String userName) {
        if (userName.length() >= USER_NAME_MAX_LENGTH || userName.length() < USER_NAME_MIN_LENGTH) {
            throw new InvalidUserContentsException(LENGTH_ERROR_MESSAGE);
        }
    }

    public boolean isSameUserName(String userName) {
        return this.userName.equals(userName);
    }
}
