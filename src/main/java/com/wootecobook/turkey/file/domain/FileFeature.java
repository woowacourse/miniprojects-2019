package com.wootecobook.turkey.file.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class FileFeature {

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private long size;

    @Builder
    public FileFeature(String path, String originalName, String type, long size) {
        this.path = path;
        this.originalName = originalName;
        this.type = type;
        this.size = size;
    }

}

