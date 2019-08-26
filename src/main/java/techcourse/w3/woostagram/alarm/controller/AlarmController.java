package techcourse.w3.woostagram.alarm.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlarmController {
    private final SimpMessagingTemplate template;

    public AlarmController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping("/alarm")
    public String alarmTest() {
        return "alarm.html";
    }

    @GetMapping("/greeting")
    public String handle() {
        template.convertAndSend("/topic/greeting/3", "hello world");
        return null;
    }
}
