package com.wootecobook.turkey;

import com.wootecobook.turkey.post.domain.Contents;
import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing
public class TurkeyApplication implements ApplicationRunner {

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        IntStream.rangeClosed(1, 100).forEach(idx -> postRepository.save(new Post(new Contents("hello" + idx))));
    }

    public static void main(String[] args) {
        SpringApplication.run(TurkeyApplication.class, args);
    }

}
