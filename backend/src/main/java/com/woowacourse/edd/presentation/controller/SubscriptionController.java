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

    @PostMapping("/{subscribedUserId}/subscribe")
    public ResponseEntity subscribe(@PathVariable Long subscribedUserId, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        subscriptionService.subscribe(subscribedUserId, sessionUser.getId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{subscribedUserId}/count-subscribers")
    public ResponseEntity countSubscribers(@PathVariable Long subscribedUserId) {
        SubscriptionCountResponse subscriptionCountResponse = subscriptionService.countSubscribers(subscribedUserId);
        return ResponseEntity.ok(subscriptionCountResponse);
    }

    @GetMapping("/{userId}/subscribed")
    public ResponseEntity<List<SubscriptionResponse>> showSubscriptions(@PathVariable Long userId) {
        List<SubscriptionResponse> subscriptions = subscriptionService.findSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/{subscribedUserId}/subscribe")
    public ResponseEntity cancelSubscription(@PathVariable Long subscribedUserId, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        subscriptionService.cancelSubscription(subscribedUserId, sessionUser.getId());
        return ResponseEntity.noContent().build();
    }
}
