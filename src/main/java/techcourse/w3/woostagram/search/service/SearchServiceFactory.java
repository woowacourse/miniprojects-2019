package techcourse.w3.woostagram.search.service;

import org.springframework.stereotype.Component;

@Component
public class SearchServiceFactory {

    private final UserSearchService userSearchService;

    public SearchServiceFactory(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    public SearchService getSearchService(String query){
        return userSearchService;
    }
}