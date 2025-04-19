package goorm.com.firstweek.member.domain.member;

import goorm.com.firstweek.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); // Post
    Optional<Member> findById(Long id); //GET
    List<Member> findAll(); // GET
    void deleteById(Long id); // DELETE
}