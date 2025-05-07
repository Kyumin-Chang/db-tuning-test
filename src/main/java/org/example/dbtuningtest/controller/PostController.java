package org.example.dbtuningtest.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dbtuningtest.model.Post;
import org.example.dbtuningtest.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponseDto> getPosts(@RequestParam(name = "memberId") Long memberId) {
        return postService.getPostsByMember(memberId);
    }

    @PostMapping
    public Post createPost(@RequestBody PostRequestDto request) {
        return postService.createPost(
                request.getMemberId(),
                request.getTitle(),
                request.getContent()
        );
    }

    @GetMapping("/offset")
    public List<PostResponseDto> getPostsByOffset(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "20", name = "size") int size) {
        return postService.getPostsByOffset(page, size);
    }

    @GetMapping("/keyset")
    public List<PostResponseDto> getPostsByKeySet(
            @RequestParam(required = false, name = "lastViewCount") Integer lastViewCount) {
        return postService.getPostsByViewCountLessThan(lastViewCount);
    }
}

