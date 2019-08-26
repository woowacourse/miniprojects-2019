package techcourse.w3.woostagram.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
