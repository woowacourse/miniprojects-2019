package techcourse.w3.woostagram.alarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class AlarmService {
    private final SimpMessagingTemplate template;

    @Autowired
    public AlarmService(SimpMessagingTemplate simpMessagingTemplate) {
        this.template = simpMessagingTemplate;
    }

    public boolean push(Long userId, String message) {
        template.convertAndSend("/topic/alarm/" + userId, message);
        return true;
    }
}
