package com.woowacourse.dsgram.domain;


import com.woowacourse.dsgram.domain.exception.InvalidUserException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(of = {"id"})
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false, length = 240)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "author", foreignKey = @ForeignKey(name = "fk_article_to_user"))
    private User author;

    public Article(String contents, String fileName, String filePath, User author) {
        this.contents = contents;
        this.fileName = fileName;
        this.filePath = filePath;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", contents='" + contents + '\'' +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", author=" + author +
                '}';
    }

    public Article update(long id, String contents) {
        if (this.id != id) {
            throw new InvalidUserException("글 작성자만 수정, 삭제가 가능합니다.");
        }
        this.contents = contents;
        return this;
    }
}
