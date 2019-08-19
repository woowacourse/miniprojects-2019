package techcourse.w3.woostagram.article.exception;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super("게시글을 찾는 데 실패했습니다.");
    }
}
