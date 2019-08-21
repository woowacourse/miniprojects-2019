package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.HashTag;
import com.woowacourse.dsgram.domain.HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HashTagService {
    private final HashTagRepository hashTagRepository;

    public HashTagService(HashTagRepository hashTagRepository) {
        this.hashTagRepository = hashTagRepository;
    }

    public void saveHashTags(List<HashTag> hashTags) {
        hashTagRepository.saveAll(hashTags);
    }
}
