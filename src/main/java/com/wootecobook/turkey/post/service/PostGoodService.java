package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.post.domain.PostGood;
import com.wootecobook.turkey.post.domain.PostGoodRepository;
import com.wootecobook.turkey.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostGoodService {

    private final PostGoodRepository postGoodRepository;

    public PostGoodService(final PostGoodRepository postGoodRepository) {
        this.postGoodRepository = postGoodRepository;
    }

    List<PostGood> good(Post post, User user) {
        Optional<PostGood> maybeGood = postGoodRepository.findByPostAndUser(post, user);

        if (maybeGood.isPresent()) {
            cancelGoodRequest(maybeGood.get());
        } else {
            approveGoodRequest(new PostGood(user, post));
        }

        return findByPost(post);
    }

    private void approveGoodRequest(PostGood postGood) {
        postGoodRepository.save(postGood);
    }

    private void cancelGoodRequest(PostGood postGood) {
        postGoodRepository.delete(postGood);
    }

    @Transactional(readOnly = true)
    List<PostGood> findByPost(Post post) {
        return postGoodRepository.findByPost(post);
    }
}
