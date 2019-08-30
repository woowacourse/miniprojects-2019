package com.wootecobook.turkey.user.service.dto;

import com.wootecobook.turkey.file.domain.FileFeature;
import com.wootecobook.turkey.file.domain.UploadFile;
import com.wootecobook.turkey.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private FileFeature profile;

    @Builder
    public UserResponse(final Long id, final String email, final String name, final FileFeature profile) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.profile = profile;
    }

    public static UserResponse from(User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();

        Optional<FileFeature> maybeProfile = getFileFeatureOfProfile(user.getProfile());
        maybeProfile.ifPresent(fileFeature -> userResponse.profile = fileFeature);

        return userResponse;
    }

    private static Optional<FileFeature> getFileFeatureOfProfile(final UploadFile profile) {
        return Optional.ofNullable(profile.getFileFeature());
    }
}
