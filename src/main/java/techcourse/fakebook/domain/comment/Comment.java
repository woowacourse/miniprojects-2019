package techcourse.fakebook.domain.comment;

import techcourse.fakebook.domain.BaseEntity;
import techcourse.fakebook.domain.article.Article;
import techcourse.fakebook.domain.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_COMMENT_TO_ARTICLE"))
    private Article article;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private boolean isPresent;

    private Comment() {}

    public Comment(String content, Article article, User user) {
        this.content = content;
        this.article = article;
        this.user = user;
        this.isPresent = true;
    }

    public void update(String content) {
        this.content = content;
    }

    public boolean isNotPresent() {
        return !isPresent;
    }

    public boolean isNotAuthor(Long id) {
        return !user.isSameWith(id);
    }

    public void delete() {
        isPresent = false;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Article getArticle() {
        return article;
    }

    public User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
