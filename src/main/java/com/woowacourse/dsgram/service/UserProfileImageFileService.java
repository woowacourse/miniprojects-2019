package com.woowacourse.dsgram.service;

import com.woowacourse.dsgram.domain.UserProfileImage;
import com.woowacourse.dsgram.domain.UserProfileImageRepository;
import com.woowacourse.dsgram.service.exception.NotFoundUserImageException;
import com.woowacourse.dsgram.service.vo.FileInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserProfileImageFileService {
    private final FileService fileService;
    private final UserProfileImageRepository userProfileImageRepository;

    public UserProfileImageFileService(FileService fileService, UserProfileImageRepository userProfileImageRepository) {
        this.fileService = fileService;
        this.userProfileImageRepository = userProfileImageRepository;
    }

    public void saveOrUpdate(Long userId, MultipartFile imageFile) {
        FileInfo fileInfo = fileService.save(imageFile);
        UserProfileImage userProfileImage = new UserProfileImage(userId, fileInfo.getFileName(), fileInfo.getFilePath());

        if (userProfileImageRepository.existsById(userId) == false) {
            userProfileImageRepository.save(userProfileImage);
            return;
        }
        update(userId, fileInfo);
    }

    @Transactional
    public void update(Long userId, FileInfo fileInfo) {
        UserProfileImage userProfileImage = userProfileImageRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserImageException("유저 프로필 사진이 조회되지 않습니다."));
        userProfileImage.update(fileInfo.getFileName(), fileInfo.getFilePath());
    }

    public UserProfileImage findById(long userId) {
        return userProfileImageRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserImageException("유저 프로필 사진이 조회되지 않습니다."));
    }
}
