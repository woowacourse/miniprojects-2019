package techcourse.w3.woostagram.alarm.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.alarm.dto.FollowsAlarmDto;
import techcourse.w3.woostagram.alarm.dto.LikesAlarmDto;
import techcourse.w3.woostagram.article.domain.Article;
import techcourse.w3.woostagram.user.domain.User;

@Service
public class AlarmService {
    private final SimpMessagingTemplate template;

    @Autowired
    public AlarmService(SimpMessagingTemplate simpMessagingTemplate) {
        this.template = simpMessagingTemplate;
    }

    public void pushLikes(User user, Article target) {
        Gson gson = new Gson();
        String message = gson.toJson(LikesAlarmDto.builder()
                .message(user.getUserContents().getUserName() + " likes your article!")
                .articleId(target.getId()));
        template.convertAndSend("/topic/alarm/likes/" + target.getUser().getId(), message);
    }

    public void pushFollows(User follower, User target) {
        Gson gson = new Gson();
        String message = gson.toJson(FollowsAlarmDto.builder()
                .message(follower.getUserContents().getUserName() + " follows you!")
                .targetName(follower.getUserContents().getUserName()));
        template.convertAndSend("/topic/alarm/follows/" + target.getId(), message);
    }
}
