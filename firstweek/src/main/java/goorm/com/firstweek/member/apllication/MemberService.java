package goorm.com.firstweek.member.apllication;

import goorm.com.firstweek.member.domain.member.MemberRepository;
import goorm.com.firstweek.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(String name, String email) {
        Member member = new Member(name, email);
        return memberRepository.save(member);
    }

    public Member get(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }
}

/** 서비스는 서비스 계층의 요소로 컨트롤러에서 매핑 및 들어온 데이터를 레파지토리에 어떻게 넘겨줄 것인지에 대한 내용을 다루게 되는 객체이다.
 *  매핑을 통해 어떤 동작을 수행해야 하는지에 대한 세부 내용을 바로 여기 서비스에서 정의하게 된다.
 *  보통 레파지토리에 어떻게 값을 넘겨줄 것인지에 대한 내용을 다룬다.
 *  DDD에서는 컨트롤러와 서비스를 위와 같이 분리하여 코딩을 진행하게 된다.
 *
 *  서비스는 서비스 어노테이션을 붙여줌으로서 서비스 형식의 메소드를 작성할 수 있게 해 주며
 *  다음과 같은 원칙으로 작성된다.
 *
 *  1. create 메소드는 문자열 name과 문자열 email을 받아들여 새로운 멤버 엔티티를 생성하고
 *     이렇게 생성된 멤버 객체(엔티티)를 이 객체의 레파지토리 필드의 save 메소드를 실행하여 실제 레파지토리에 저장한다.
 *  2. get 메소드는 id값을 파라미터로 받아 레파지토리 필드의 findById메소드를 통해 객체를 리턴하고 없으면 널값을 리턴하게 설계한다.
 *  3. getAll 메소드는 레파지토리 필드의 findAll 메소드를 실행하여 모든 멤버 정보를 반환한다.
 */