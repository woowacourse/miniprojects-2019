package techcourse.w3.woostagram.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class FollowsAlarmDto {
    private String message;
    private String targetName;

    @Builder
    public FollowsAlarmDto(String message, String targetName) {
        this.message = message;
        this.targetName = targetName;
    }
}
