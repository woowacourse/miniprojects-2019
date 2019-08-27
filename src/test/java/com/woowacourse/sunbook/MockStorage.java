package com.woowacourse.sunbook;

import com.woowacourse.sunbook.application.dto.article.ArticleResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserRequestDto;
import com.woowacourse.sunbook.application.dto.user.UserResponseDto;
import com.woowacourse.sunbook.application.dto.user.UserUpdateRequestDto;
import com.woowacourse.sunbook.application.service.ArticleService;
import com.woowacourse.sunbook.application.service.CommentService;
import com.woowacourse.sunbook.application.service.UserService;
import com.woowacourse.sunbook.domain.article.Article;
import com.woowacourse.sunbook.domain.article.ArticleFeature;
import com.woowacourse.sunbook.domain.article.ArticleRepository;
import com.woowacourse.sunbook.domain.comment.Comment;
import com.woowacourse.sunbook.domain.comment.CommentFeature;
import com.woowacourse.sunbook.domain.comment.CommentRepository;
import com.woowacourse.sunbook.domain.relation.Relation;
import com.woowacourse.sunbook.domain.relation.RelationRepository;
import com.woowacourse.sunbook.domain.reaction.ReactionArticle;
import com.woowacourse.sunbook.domain.reaction.ReactionArticleRepository;
import com.woowacourse.sunbook.domain.reaction.ReactionComment;
import com.woowacourse.sunbook.domain.reaction.ReactionCommentRepository;
import com.woowacourse.sunbook.domain.user.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class MockStorage {

    @Mock
    protected ArticleFeature articleFeature;

    @Mock
    protected ArticleResponseDto articleResponseDto;

    @Mock
    protected ArticleFeature updatedArticleFeature;

    @Mock
    protected ArticleRepository articleRepository;

    @Mock
    protected ModelMapper modelMapper;

    @Mock
    protected Article article;

    @Mock
    protected User user;

    @Mock
    protected UserService userService;

    @Mock
    protected CommentRepository commentRepository;
    
    @Mock
    protected ArticleService articleService;

    @Mock
    protected Comment comment;

    @Mock
    protected UserRepository userRepository;
    
    @Mock
    protected UserRequestDto userRequestDto;

    @Mock
    protected UserResponseDto userResponseDto;

    @Mock
    protected ReactionArticleRepository reactionArticleRepository;

    @Mock
    protected User author;

    @Mock
    protected ReactionArticle reactionArticle;

    @Mock
    protected UserUpdateRequestDto userUpdateRequestDto;

    @Mock
    protected UserChangePassword userChangePassword;

    @Mock
    protected UserEmail userEmail;

    @Mock
    protected UserName userName;

    @Mock
    protected UserPassword userPassword;

    @Mock
    protected User writer;

    @Mock
    protected User other;

    @Mock
    protected Article otherArticle;

    @Mock
    protected CommentFeature commentFeature;

    @Mock
    protected ReactionCommentRepository reactionCommentRepository;

    @Mock
    protected CommentService commentService;

    @Mock
    protected ReactionComment reactionComment;

    @Mock
    protected RelationRepository relationRepository;

    @Mock
    protected User from;

    @Mock
    protected Relation fromRelation;
}