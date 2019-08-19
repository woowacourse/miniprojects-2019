package com.woowacourse.edd.domain;

import com.woowacourse.edd.domain.vo.Contents;
import com.woowacourse.edd.domain.vo.Title;
import com.woowacourse.edd.domain.vo.YoutubeId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private YoutubeId youtubeId;

    @Embedded
    private Title title;

    @Embedded
    private Contents contents;

    @CreationTimestamp()
    private LocalDateTime createDate;

    private Video() {
    }

    public Video(YoutubeId youtubeId, Title title, Contents contents) {
        this.youtubeId = youtubeId;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }

    public String getYoutubeId() {
        return youtubeId.getYoutubeId();
    }

    public String getTitle() {
        return title.getTitle();
    }

    public String getContents() {
        return contents.getContents();
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
