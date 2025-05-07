package org.example.dbtuningtest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.example.dbtuningtest.model.Member;
import org.example.dbtuningtest.model.Post;
import org.example.dbtuningtest.repository.MemberRepository;
import org.example.dbtuningtest.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DummyDataService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final Random random = new Random();

    public void insertDummyData() {
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 1000; i++) {
            Member member = Member.builder()
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .createdAt(LocalDateTime.now())
                    .build();

            members.add(member);
        }
        memberRepository.saveAll(members);
    }

    public void insertDummyPosts() {
        List<Member> members = memberRepository.findAll();
        List<Post> posts = new ArrayList<>();
        LocalDateTime baseTime = LocalDateTime.now();

        for (Member member : members) {
            for (int j = 1; j <= 500; j++) {
                Post post = Post.builder()
                        .title("Title " + j)
                        .content("This is the content for post " + j)
                        .createdAt(baseTime.plusSeconds(j))
                        .viewCount(random.nextInt(100_000))
                        .member(member)
                        .build();

                posts.add(post);

                if (posts.size() % 1000 == 0) {
                    postRepository.saveAll(posts);
                    postRepository.flush();
                    posts.clear();
                }
            }
        }

        if (!posts.isEmpty()) {
            postRepository.saveAll(posts);
            postRepository.flush();
        }
    }
}

