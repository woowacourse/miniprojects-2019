package com.woowacourse.edd.domain;

import com.woowacourse.edd.exceptions.InvalidAccessException;
import com.woowacourse.edd.exceptions.InvalidContentsException;
import com.woowacourse.edd.exceptions.UnauthorizedAccessException;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Comment {

    public static final int CONTENTS_LENGTH_MAX = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 1)
    @Lob
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateDate;

    public Comment() {
    }

    public Comment(String contents, Video video, User author) {
        checkContents(contents);
        this.contents = contents.trim();
        this.video = video;
        this.author = author;
    }

    private void checkContents(String contents) {
        if (Objects.isNull(contents) || contents.trim().isEmpty()) {
            throw new InvalidContentsException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getContents() {
        return contents;
    }

    public Video getVideo() {
        return video;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void update(String contents, Long userId, Long videoId) {
        checkControllable(userId, videoId);
        this.contents = contents;
    }

    public void checkControllable(Long userId, Long videoId) {
        checkUser(userId);
        checkVideo(videoId);
    }

    private void checkUser(Long userId) {
        if (author.isNotMatch(userId)) {
            throw new UnauthorizedAccessException();
        }
    }

    private void checkVideo(Long videoId) {
        if (video.isNotMatch(videoId)) {
            throw new InvalidAccessException();
        }
    }
}
