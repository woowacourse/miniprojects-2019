package techcourse.w3.woostagram.article.exception;

import techcourse.w3.woostagram.common.exception.WoostagramException;

public class ArticleNotFoundException extends WoostagramException {
    private static final String ERROR_ARTICLE_NOT_FOUND = "게시글을 찾는 데 실패했습니다.";

    public ArticleNotFoundException() {
        super(ERROR_ARTICLE_NOT_FOUND);
    }
}
