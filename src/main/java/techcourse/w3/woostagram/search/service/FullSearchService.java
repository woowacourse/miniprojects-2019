package techcourse.w3.woostagram.search.service;

import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.search.dto.SearchDto;
import techcourse.w3.woostagram.user.dto.UserInfoDto;

import java.util.List;

@Service
public class FullSearchService {
    private final UserSearchService userSearchService;

    public FullSearchService(final UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    //TODO: 해시태그 추가
    public SearchDto search(String query) {
        List<UserInfoDto> users = userSearchService.search(query);
        return SearchDto.from(users);
    }
}
