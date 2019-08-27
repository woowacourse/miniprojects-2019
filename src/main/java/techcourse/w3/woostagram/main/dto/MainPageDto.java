package techcourse.w3.woostagram.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import techcourse.w3.woostagram.article.dto.ArticleDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class MainPageDto {
    private UserInfoDto loggedInUser;
    private List<ArticleDto> articles;

    @Builder
    public MainPageDto(UserInfoDto loggedInUser, List<ArticleDto> articles) {
        this.loggedInUser = loggedInUser;
        this.articles = articles;
    }

    public static MainPageDto from(UserInfoDto loggedInUser, List<ArticleDto> articles) {
        return MainPageDto.builder()
                .loggedInUser(loggedInUser)
                .articles(articles)
                .build();
    }
}
