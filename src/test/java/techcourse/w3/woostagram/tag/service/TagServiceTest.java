package techcourse.w3.woostagram.tag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import techcourse.w3.woostagram.tag.domain.Tag;
import techcourse.w3.woostagram.tag.domain.TagRepository;

import java.util.Optional;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @Test
    public void parse_correct_success() {
        when(tagRepository.save(any(Tag.class))).thenReturn(any(Tag.class));
        assertThat(tagService.parse("abc #abc", Pattern.compile("#[A-Za-zㄱ-힣0-9_]+")).size()).isEqualTo(1);
    }

    @Test
    public void parse_incorrect_fail() {
        when(tagRepository.save(any(Tag.class))).thenReturn(any(Tag.class));
        assertThat(tagService.parse("abc #def #abc", Pattern.compile("#[A-Za-zㄱ-힣0-9_]+")).size()).isNotEqualTo(1);
    }
}
