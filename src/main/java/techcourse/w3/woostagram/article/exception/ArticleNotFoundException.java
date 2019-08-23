package techcourse.w3.woostagram.article.exception;

public class ArticleNotFoundException extends RuntimeException {
    private static final String ERROR_ARTICLE_NOT_FOUND = "게시글을 찾는 데 실패했습니다.";

    public ArticleNotFoundException() {
        super(ERROR_ARTICLE_NOT_FOUND);
    }
}
