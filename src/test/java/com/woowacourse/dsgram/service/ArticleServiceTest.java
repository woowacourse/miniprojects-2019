package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.Article;
import com.woowacourse.dsgram.domain.ArticleRepository;
import com.woowacourse.dsgram.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class ArticleServiceTest {

    private Article article;

    @InjectMocks
    private ArticleService articleService;

    @Mock
    ArticleRepository articleRepository;


    @BeforeEach
    void setUp() {
        User user = User.builder()
                .userName("test")
                .nickName("test")
                .email("test@gmail.com")
                .password("test123")
                .webSite("")
                .intro("")
                .build();
        article = new Article("contents", "fileName", "filePath", user);
    }

    @Test
    void 게시글_생성_성공() {
        given(articleRepository.save(article)).willReturn(article);

        articleService.create(article);

        verify(articleRepository).save(article);
    }

    @Test
    void 게시글_조회_성공() {
        given(articleRepository.findById(any())).willReturn(Optional.of(article));
        articleService.findById(1L);
        verify(articleRepository).findById(anyLong());
    }

    @Test
    void 게시글_조회_실패() {
        articleService.create(article);
        assertThrows(EntityNotFoundException.class, () -> articleService.findById(1L));
    }
}