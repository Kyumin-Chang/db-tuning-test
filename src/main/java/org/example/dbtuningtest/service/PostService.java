package org.example.dbtuningtest.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.dbtuningtest.controller.PostResponseDto;
import org.example.dbtuningtest.model.Member;
import org.example.dbtuningtest.model.Post;
import org.example.dbtuningtest.repository.MemberRepository;
import org.example.dbtuningtest.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public List<PostResponseDto> getPostsByMember(Long memberId) {
        List<Post> posts = postRepository.findByMemberIdOrderByViewCountDesc(memberId);

        return posts.stream()
                .map(post -> PostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .memberName(post.getMember().getUsername())
                        .viewCount(post.getViewCount())
                        .build())
                .toList();
    }

    public Post createPost(Long memberId, String title, String content) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Post post = Post.builder()
                .title(title)
                .content(content)
                .createdAt(LocalDateTime.now())
                .member(member)
                .build();

        return postRepository.save(post);
    }

    public List<PostResponseDto> getPostsByOffset(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "viewCount"));
        Page<Post> postsPage = postRepository.findAllWithPagination(pageable);

        return postsPage.stream()
                .map(post -> PostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .memberName(post.getMember().getUsername())
                        .viewCount(post.getViewCount())
                        .build())
                .toList();
    }

    public List<PostResponseDto> getPostsByViewCountLessThan(Integer lastViewCount) {
        List<Post> posts;

        if (lastViewCount == null) {
            posts = postRepository.findTop20ByOrderByViewCountDesc();
        } else {
            posts = postRepository.findTop20ByViewCountLessThanOrderByViewCountDesc(lastViewCount);
        }

        return posts.stream()
                .map(post -> PostResponseDto.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .createdAt(post.getCreatedAt())
                        .memberName(post.getMember().getUsername())
                        .viewCount(post.getViewCount())
                        .build())
                .toList();
    }


}


