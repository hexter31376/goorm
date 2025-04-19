package goorm.com.firstweek.member.infrastructure;

import goorm.com.firstweek.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long> {
}