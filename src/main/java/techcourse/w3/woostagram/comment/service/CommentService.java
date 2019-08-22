package techcourse.w3.woostagram.comment.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.article.service.ArticleService;
import techcourse.w3.woostagram.comment.domain.Comment;
import techcourse.w3.woostagram.comment.domain.CommentRepository;
import techcourse.w3.woostagram.comment.dto.CommentDto;
import techcourse.w3.woostagram.comment.exception.CommentNotFoundException;
import techcourse.w3.woostagram.common.exception.UnauthorizedException;
import techcourse.w3.woostagram.user.domain.User;
import techcourse.w3.woostagram.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public CommentService(final CommentRepository commentRepository, final UserService userService, final ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    public CommentDto save(CommentDto commentDto, String email, Long articleId) {
        User user = userService.findUserByEmail(email);
        Article article = articleService.findArticleById(articleId);
        Comment comment = commentRepository.save(commentDto.toEntity(user, article));
        return CommentDto.from(comment, user.getId());
    }

    public List<CommentDto> findByArticleId(Long articleId, String email) {
        User loggedUser = userService.findUserByEmail(email);
        return commentRepository.findByArticle_Id(articleId).stream()
                .map(comment -> CommentDto.from(comment, loggedUser.getId()))
                .collect(Collectors.toList());
    }

    public void deleteById(Long commentId, String email) {
        validateUser(commentId, email);

        commentRepository.deleteById(commentId);
    }

    private void validateUser(Long commentId, String email) {
        User user = userService.findUserByEmail(email);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if (!user.equals(comment.getUser())) {
            throw new UnauthorizedException();
        }
    }
}
