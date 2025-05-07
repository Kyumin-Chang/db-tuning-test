package org.example.dbtuningtest.repository;

import org.example.dbtuningtest.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
