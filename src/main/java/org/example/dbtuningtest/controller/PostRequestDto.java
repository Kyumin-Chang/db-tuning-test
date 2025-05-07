package org.example.dbtuningtest.controller;

import lombok.Data;

@Data
public class PostRequestDto {
    private Long memberId;
    private String title;
    private String content;
}

