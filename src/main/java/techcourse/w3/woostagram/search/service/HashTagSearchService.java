package techcourse.w3.woostagram.search.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.tag.domain.HashTag;
import techcourse.w3.woostagram.tag.dto.TagDto;
import techcourse.w3.woostagram.tag.service.HashTagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HashTagSearchService implements SearchService<TagDto> {
    private final HashTagService hashTagService;

    public HashTagSearchService(final HashTagService hashTagService) {
        this.hashTagService = hashTagService;
    }

    @Override
    public List<TagDto> search(String query) {
        return hashTagService.findByNameContaining(query).stream()
                .map(HashTag::getTag)
                .distinct()
                .map(TagDto::from)
                .collect(Collectors.toList());
    }
}
