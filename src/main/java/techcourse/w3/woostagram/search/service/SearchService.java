package techcourse.w3.woostagram.search.service;

import java.util.List;

public interface SearchService<T> {
    List<T> search(String query);
}
