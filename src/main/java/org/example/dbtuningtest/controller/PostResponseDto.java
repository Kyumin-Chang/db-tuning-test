package org.example.dbtuningtest.controller;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String memberName;
    private Integer viewCount;
}

