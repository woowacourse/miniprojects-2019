package com.wootecobook.turkey.post.service;

import com.wootecobook.turkey.commons.Good;
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
public class PostGoodService implements GoodService<PostGood, Post> {

    private final PostGoodRepository postGoodRepository;

    public PostGoodService(final PostGoodRepository postGoodRepository) {
        this.postGoodRepository = postGoodRepository;
    }

    @Override
    public List<PostGood> toggleGood(Post post, User user) {
        Optional<PostGood> maybeGood = postGoodRepository.findByPostAndUser(post, user);

        if (maybeGood.isPresent()) {
            cancelGoodRequest(maybeGood.get());
        } else {
            approveGoodRequest(new PostGood(user, post));
        }

        return findBy(post);
    }

    private void approveGoodRequest(PostGood postGood) {
        postGoodRepository.save(postGood);
    }

    private void cancelGoodRequest(PostGood postGood) {
        postGoodRepository.delete(postGood);
    }

    @Override
    public List<PostGood> findBy(final Post post) {
        return postGoodRepository.findByPost(post);
    }
}
