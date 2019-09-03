package techcourse.w3.woostagram.tag.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcourse.w3.woostagram.tag.domain.Tag;
import techcourse.w3.woostagram.tag.domain.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(final TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> saveAll(List<Tag> tags) {
        return tags.stream().map(this::getOrCreate).collect(Collectors.toList());
    }

    private Tag getOrCreate(Tag tag) {
        return tagRepository.findByName(tag.getName()).orElseGet(() -> tagRepository.save(tag));
    }

    public List<Tag> parse(String contents, Pattern pattern) {
        Matcher matcher = pattern.matcher(contents);
        List<String> tags = new ArrayList<>();

        while (matcher.find()) {
            tags.add(matcher.group());
        }

        return saveAll(tags.stream()
                .map(tag -> Tag.builder().name(tag).build())
                .collect(Collectors.toList()));
    }
}
