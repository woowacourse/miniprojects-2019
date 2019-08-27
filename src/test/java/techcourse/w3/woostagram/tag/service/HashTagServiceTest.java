package techcourse.w3.woostagram.tag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.tag.domain.HashTag;
import techcourse.w3.woostagram.tag.domain.HashTagRepository;
import techcourse.w3.woostagram.tag.domain.Tag;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HashTagServiceTest {
    @InjectMocks
    private HashTagService hashTagService;

    @Mock
    private HashTagRepository hashTagRepository;

    @Mock
    private TagService tagService;

    @Test
    void save_correct_success() {
        Article article = Article.builder().contents("hello world").build();
        Tag tag = Tag.builder().build();
        HashTag hashTag = HashTag.builder().article(article).tag(tag).build();

        when(tagService.parse(anyString(), anyObject())).thenReturn(Arrays.asList(tag));

        hashTagService.save(article, article.getContents());

        verify(hashTagRepository, times(1)).save(hashTag);
    }
}