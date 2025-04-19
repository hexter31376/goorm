package goorm.com.firstweek.member.infrastructure;

import goorm.com.firstweek.domain.member.Member;
import goorm.com.firstweek.member.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemberRepository implements MemberRepository {

    private final SpringDataJpaMemberRepository jpaRepo;

    @Override
    public Member save(Member member) {
        return jpaRepo.save(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return jpaRepo.findById(id);
    }

    @Override
    public List<Member> findAll() {
        return jpaRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepo.deleteById(id);
    }
}
