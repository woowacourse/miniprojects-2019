package techcourse.w3.woostagram.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LikesAlarmDto {
    private String message;
    private Long articleId;

    @Builder
    public LikesAlarmDto(String message, Long articleId) {
        this.message = message;
        this.articleId = articleId;
    }
}
