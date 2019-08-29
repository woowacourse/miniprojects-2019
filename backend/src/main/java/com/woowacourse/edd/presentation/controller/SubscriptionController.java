package com.woowacourse.edd.presentation.controller;

import com.woowacourse.edd.application.response.SessionUser;
import com.woowacourse.edd.application.response.SubscriptionCountResponse;
import com.woowacourse.edd.application.response.SubscriptionResponse;
import com.woowacourse.edd.application.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.woowacourse.edd.presentation.controller.UserController.USER_URL;

@RestController
@RequestMapping(USER_URL)
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/{subscribedId}/subscribe")
    public ResponseEntity subscribe(@PathVariable Long subscribedId, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        subscriptionService.subscribe(subscribedId, sessionUser.getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{subscribedId}/count-subscribers")
    public ResponseEntity countSubscribers(@PathVariable Long subscribedId) {
        SubscriptionCountResponse subscriptionCountResponse = subscriptionService.countSubscribers(subscribedId);
        return ResponseEntity.ok(subscriptionCountResponse);
    }

    @GetMapping("/{id}/subscribed")
    public ResponseEntity<List<SubscriptionResponse>> showSubsrcriptions(@PathVariable Long id) {
        List<SubscriptionResponse> subscriptions = subscriptionService.findSubscriptions(id);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{subscribedId}/subscribe")
    public ResponseEntity cancelSubscription(@PathVariable Long subscribedId, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        subscriptionService.cancelSubscription(subscribedId, sessionUser.getId());
        return ResponseEntity.noContent().build();
    }
}
