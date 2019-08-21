package techcourse.w3.woostagram.comment.dto;

import lombok.*;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.comment.domain.Comment;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CommentDto {
    private Long id;
    private String contents;
    private LocalDateTime createdDate;
    private UserInfoDto userInfoDto;
    private boolean mine;

    @Builder
    public CommentDto(Long id, String contents, LocalDateTime createdDate, UserInfoDto userInfoDto, boolean mine) {
        this.id = id;
        this.contents = contents;
        this.createdDate = createdDate;
        this.userInfoDto = userInfoDto;
        this.mine = mine;
    }

    public static CommentDto from(Comment comment, long loggedInUserId) {
        return CommentDto.builder()
                .id(comment.getId())
                .contents(comment.getContents())
                .createdDate(comment.getCreatedDate())
                .userInfoDto(UserInfoDto.from(comment.getUser()))
                .mine(comment.isAuthor(loggedInUserId))
                .build();
    }

    public Comment toEntity(User user, Article article) {
        return Comment.builder()
                .contents(contents)
                .user(user)
                .article(article)
                .build();
    }
}
