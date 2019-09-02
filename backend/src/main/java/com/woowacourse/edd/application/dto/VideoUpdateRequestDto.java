package com.woowacourse.edd.application.dto;

import javax.validation.constraints.Size;

import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_CONTENTS_MESSAGE;
import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_TITLE_MESSAGE;
import static com.woowacourse.edd.application.dto.VideoSaveRequestDto.OVER_SIZE_YOUTUBEID_MESSAGE;
import static com.woowacourse.edd.domain.Video.CONTENTS_LENGTH_MAX;
import static com.woowacourse.edd.domain.Video.TITLE_LENGTH_MAX;
import static com.woowacourse.edd.domain.Video.YOUTUBEID_LENGTH_MAX;

public class VideoUpdateRequestDto {

    @Size(max = YOUTUBEID_LENGTH_MAX, message = OVER_SIZE_YOUTUBEID_MESSAGE)
    private String youtubeId;
    @Size(max = TITLE_LENGTH_MAX, message = OVER_SIZE_TITLE_MESSAGE)
    private String title;
    @Size(max = CONTENTS_LENGTH_MAX, message = OVER_SIZE_CONTENTS_MESSAGE)
    private String contents;

    private VideoUpdateRequestDto() {
    }

    public VideoUpdateRequestDto(String youtubeId, String title, String contents) {
        this.youtubeId = youtubeId;
        this.title = title;
        this.contents = contents;
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
}
