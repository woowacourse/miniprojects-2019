package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostGood;
import com.wootecobook.turkey.post.domain.PostGoodRepository;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostGoodService {

    private final PostGoodRepository postGoodRepository;

    public PostGoodService(final PostGoodRepository postGoodRepository) {
        this.postGoodRepository = postGoodRepository;
    }

    Integer good(Post post, User user) {
        Optional<PostGood> maybeGood = postGoodRepository.findByPostAndUser(post, user);

        if (maybeGood.isPresent()) {
            postGoodRepository.delete(maybeGood.get());
        } else {
            postGoodRepository.save(new PostGood(user, post));
        }

        return postGoodRepository.countByPost(post);
    }

    Integer countByPost(Post post) {
        return postGoodRepository.countByPost(post);
    }
}
