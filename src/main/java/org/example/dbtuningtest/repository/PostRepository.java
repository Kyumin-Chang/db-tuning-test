package org.example.dbtuningtest.repository;

import java.util.List;
import org.example.dbtuningtest.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "member")
    List<Post> findByMemberIdOrderByViewCountDesc(Long memberId);

    @EntityGraph(attributePaths = "member")
    @Query("SELECT p FROM Post p ORDER BY p.viewCount DESC")
    Page<Post> findAllWithPagination(Pageable pageable);

    @EntityGraph(attributePaths = "member")
    List<Post> findTop20ByViewCountLessThanOrderByViewCountDesc(Integer viewCount);

    @EntityGraph(attributePaths = "member")
    List<Post> findTop20ByOrderByViewCountDesc();
}

