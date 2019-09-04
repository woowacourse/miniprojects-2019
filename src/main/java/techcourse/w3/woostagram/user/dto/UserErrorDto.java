package techcourse.w3.woostagram.user.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserErrorDto {
    private String errorCode;
    private String errorMessage;
}
