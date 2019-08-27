package techcourse.w3.woostagram.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.tag.dto.TagDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SearchDto {
    private List<UserInfoDto> userInfoDtos;
    private List<TagDto> hashTags;

    @Builder
    public SearchDto(final List<UserInfoDto> userInfoDtos, final List<TagDto> hashTags) {
        this.userInfoDtos = userInfoDtos;
        this.hashTags = hashTags;
    }

    public static SearchDto from(List<UserInfoDto> userInfoDtos, List<TagDto> hashTags) {
        return SearchDto.builder()
                .userInfoDtos(userInfoDtos)
                .hashTags(hashTags)
                .build();
    }
}
