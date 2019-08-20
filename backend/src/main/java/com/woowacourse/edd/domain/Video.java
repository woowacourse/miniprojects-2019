package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.InvalidTitleException;
import com.woowacourse.edd.exceptions.InvalidYoutubeIdException;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String youtubeId;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    @Column(nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    private Video() {
    }

    public Video(String youtubeId, String title, String contents) {
        checkYoutubeId(youtubeId);
        checkTitle(title);
        checkContents(contents);
        this.youtubeId = youtubeId.trim();
        this.title = title.trim();
        this.contents = contents.trim();
    }

    private void checkContents(String contents) {
        if (Objects.isNull(contents) || contents.trim().isEmpty()) {
            throw new InvalidContentsException();
        }
    }

    private void checkTitle(String title) {
        if (Objects.isNull(title) || title.trim().isEmpty()) {
            throw new InvalidTitleException();
        }
    }

    private void checkYoutubeId(String youtubeId) {
        if (Objects.isNull(youtubeId) || youtubeId.trim().isEmpty()) {
            throw new InvalidYoutubeIdException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + id +
            ", youtubeId=" + youtubeId +
            ", title=" + title +
            ", contents=" + contents +
            ", createDate=" + createDate +
            '}';
    }
}
