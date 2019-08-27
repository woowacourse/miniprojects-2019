package com.woowacourse.sunbook.application.service;

import com.woowacourse.sunbook.application.dto.comment.CommentResponseDto;
import com.woowacourse.sunbook.application.exception.NotFoundCommentException;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.comment.CommentFeature;
import com.woowacourse.sunbook.domain.comment.CommentRepository;
import com.woowacourse.sunbook.domain.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(final CommentRepository commentRepository,
                          final UserService userService,
                          final ArticleService articleService,
                          final ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public CommentResponseDto save(final CommentFeature commentFeature,
                                   final Long articleId,
                                   final Long userId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.save(new Comment(commentFeature, user, article));

        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        return Collections.unmodifiableList(
                commentRepository.findAll().stream()
                        .map(article -> modelMapper.map(article, CommentResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findByArticleId(final Long articleId) {
        return Collections.unmodifiableList(
                commentRepository.findByArticleId(articleId).stream()
                        .map(article -> modelMapper.map(article, CommentResponseDto.class))
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public CommentResponseDto modify(final Long commentId,
                                     final CommentFeature commentFeature,
                                     final Long articleId,
                                     final Long userId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        comment.modify(commentFeature, user, article);

        return modelMapper.map(comment, CommentResponseDto.class);
    }

    @Transactional
    public Boolean remove(final Long commentId,
                          final Long articleId,
                          final Long userId) {
        User user = userService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        comment.validateAuth(user, article);
        commentRepository.delete(comment);

        return true;
    }

    // TODO: CommentService 안에서 findById()를 사용하는 부분을 이 메서드로 변경하기
    protected Comment findById(final Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
    }
}
