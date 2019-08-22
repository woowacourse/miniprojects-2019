package com.wootecobook.turkey.user.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 다섯명만_검색되는지_확인() {
        // given
        final String name = "name";
        userRepository.save(new User("UserRepoTest1@gmail.com", "A" + name, "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest2@gmail.com", name + "A", "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest3@gmail.com", "qwrqwr" + name, "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest4@gmail.com", "qwrqwr" + name, "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest5@gmail.com", "qwrqwr" + name, "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest6@gmail.com", "qwrqwr" + name, "P@ssw0rd"));
        userRepository.save(new User("UserRepoTest7@gmail.com", "qwrqwr" + name, "P@ssw0rd"));

        // when
        final List<User> users = userRepository.findTop5ByNameIsContaining(name);

        // then
        assertThat(users).hasSize(5);
    }
}