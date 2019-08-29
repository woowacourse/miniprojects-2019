package techcourse.w3.woostagram.search.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.search.dto.SearchDto;
import techcourse.w3.woostagram.tag.dto.TagDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@Service
public class FullSearchService {
    private final UserSearchService userSearchService;
    private final HashTagSearchService hashTagSearchService;

    public FullSearchService(final UserSearchService userSearchService, final HashTagSearchService hashTagSearchService) {
        this.userSearchService = userSearchService;
        this.hashTagSearchService = hashTagSearchService;
    }

    public SearchDto search(String query) {
        List<UserInfoDto> users = userSearchService.search(query);
        List<TagDto> hashTags = hashTagSearchService.search(query);
        return SearchDto.from(users, hashTags);
    }
}
