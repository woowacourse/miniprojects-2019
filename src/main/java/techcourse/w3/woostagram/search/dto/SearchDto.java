package techcourse.w3.woostagram.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchDto {
    private List<UserInfoDto> userInfoDtos;

    @Builder
    public SearchDto(final List<UserInfoDto> userInfoDtos) {
        this.userInfoDtos = userInfoDtos;
    }

    public static SearchDto from(List<UserInfoDto> userInfoDtos) {
        return SearchDto.builder()
                .userInfoDtos(userInfoDtos)
                .build();
    }
}
