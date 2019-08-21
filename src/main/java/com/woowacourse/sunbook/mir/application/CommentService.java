package com.woowacourse.sunbook.mir.application;

import com.woowacourse.sunbook.application.article.ArticleService;
import com.woowacourse.sunbook.application.user.LoginService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.user.User;
import com.woowacourse.sunbook.mir.application.dto.CommentResponseDto;
import com.woowacourse.sunbook.mir.application.exception.NotFoundCommentException;
import com.woowacourse.sunbook.mir.domain.Comment;
import com.woowacourse.sunbook.mir.domain.CommentFeature;
import com.woowacourse.sunbook.mir.domain.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final LoginService loginService;
    private final CommentRepository commentRepository;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentService(final LoginService loginService,
                          final CommentRepository commentRepository,
                          final ArticleService articleService,
                          final ModelMapper modelMapper) {
        this.loginService = loginService;
        this.commentRepository = commentRepository;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    // TODO : articleService 에 findById 추가
    @Transactional
    public CommentResponseDto save(final CommentFeature commentFeature,
                                   final Long articleId,
                                   final Long userId) {
        User user = loginService.findById(userId);
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

    @Transactional
    public CommentResponseDto modify(final Long commentId,
                                     final CommentFeature commentFeature,
                                     final Long articleId,
                                     final Long userId) {
        User user = loginService.findById(userId);
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
        User user = loginService.findById(userId);
        Article article = articleService.findById(articleId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);
        comment.validateAuth(user, article);
        commentRepository.delete(comment);

        return true;
    }
}
