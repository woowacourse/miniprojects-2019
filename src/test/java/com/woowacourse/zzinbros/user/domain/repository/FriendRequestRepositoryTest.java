package com.woowacourse.zzinbros.user.domain.repository;

import com.woowacourse.zzinbros.user.domain.FriendRequest;
import com.woowacourse.zzinbros.user.domain.User;
import com.woowacourse.zzinbros.user.domain.UserBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FriendRequestRepositoryTest extends UserBaseTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FriendRequestRepository friendRequestRepository;


    @Test
    @DisplayName("요청자와 수용자 동시 만족하는 친구 요청 삭제")
    void deleteBySenderAndReceiver() {
        User user1 = testEntityManager.persist(new User("aa", "aa@aa.com", "12345678"));
        User user2 = testEntityManager.persist(new User("bb", "bb@aa.com", "12345678"));
        User user3 = testEntityManager.persist(new User("cc", "cc@aa.com", "12345678"));

        FriendRequest friendRequest1 = testEntityManager.persist(new FriendRequest(user1, user2));
        FriendRequest friendRequest2 = testEntityManager.persist(new FriendRequest(user3, user2));

        testEntityManager.flush();
        testEntityManager.clear();

        friendRequestRepository.deleteBySenderAndReceiver(user1, user2);
        assertThat(friendRequestRepository.findAllByReceiver(user2).size()).isEqualTo(1);
        assertThat(friendRequestRepository.existsBySenderAndReceiver(user1, user2)).isFalse();

    }
}