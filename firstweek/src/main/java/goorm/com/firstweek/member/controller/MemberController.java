package goorm.com.firstweek.member.controller;

import goorm.com.firstweek.member.apllication.MemberService;
import goorm.com.firstweek.member.apllication.dto.MemberForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
// 컨트롤러 객체임을 지정하는 어노테이션, Model 객체를 사용할 수 있게 함과 동시에 return 값에 해당하는 뷰로 매핑을 도와준다.
@RequestMapping("/members") // /members 로 매핑되어 요청이 들어왔을때 다음 컨트롤러를 호출할 것
public class MemberController { // 멤버 컨트롤러
    private final MemberService memberService; // 멤버서비스 객체를 받아들일 수 있게 하고 이 멤버서비스는 외부에서 주입받는다.

    public MemberController(MemberService memberService) { // 멤버서비스 생성자
        this.memberService = memberService;
    }

    @GetMapping// /members 로 GET 요청이 들어왔을때 다음 메소드를 실행하라
    public String list(Model model) { // 뷰에 뿌리기 위한 모델을 의존성 주입으로 받아들이기
        model.addAttribute("members", memberService.getAll()); // addAttribute로 members라는 이름을 가진 뷰 요소에, getAll메소드의 결과값을 value로 가진 값으로 넣어준다.
        return "members/list"; // member/list 인 뷰에 해당 지정 변환값을 반환할것
    }

    @GetMapping("/new") // /members/new로 GET요청이 들어왔을경우
    public String form(Model model) { // 뷰에 뿌리기 위한 모델을 의존성 주입으로 받아들이기
        model.addAttribute("memberForm", new MemberForm());
        // userForm의 필드명 및 타입이랑 일치하는 요소에 자동 매핑하여 넣어준다.
        // memberForm이라는 이름으로 빈 폼 객체를 뷰에 전달하여, 타임리프에서 ${memberForm}으로 참조 가능하게 한다.
        return "members/create"; // member/create 인 뷰에 해당 지정 변환값을 반환할것
    }

    @PostMapping// /members로 POST 요청이 들어왔을경우
    public String create(@ModelAttribute MemberForm form) { // HTTP 형식으로 들어있는 폼의 필드를 MemberForm에 필드에 매핑시켜 넣어주고 그 MemberForm을 모델에 넣어준다.
        memberService.create(form.getName(), form.getEmail()); // MemberForm에 들어있는 이름과 이메일을 꺼내 memberService의 create메소드를 호출하고 파라미터로 '값'을 넣어준다.
        return "redirect:/members";
    }
}

/**
 * 컨트롤러는 프레젠테이션 계층으로 실제 들어온 CRUD 요청을 어떻게 매핑할건지에 대한 내용을 다루게 된다
 * 레파지토리에 넘겨주는 행위는 서비스에서 정의하게 된다.
 * DDD에서는 컨트롤러와 서비스를 위와 같이 분리하여 코딩을 진행하게 된다.
 * 이를 통해 MVC 모델의 컨트롤러가 단일 책임 원칙을 준수할 수 있게 된다.
 *
 * 컨트롤러는 컨트롤러 어노테이션을 붙여줌으로서 컨트롤러 형식의 메소드를 작성할 수 있게 해주며
 * 다음과 같은 원칙으로 작동된다.
 *
 * 1. 컨트롤러 객체는 @RequestMapping을 통해 주소/members 와 같은 URL을 주었을때 적절한 매핑이 달린 메소드를 실행시킬 수 있다.
 *    이때 GET 요청일 경우 ()가 없는 list 메소드를 호출하게 된다
 *
 * 2. list 메소드는 뷰에 넘겨줄 Model형 model을 의존성 주입으로 받아들이며 이와 같이 처리하는 이유는
 *    스프링이 상황에 맞게 만들어 주는 Model형 객체를 유연하게 받아들여 사용할 수 있기 때문이다. 이는 의존성 주입의 대표적인 예시이다.
 *    그리고 모델의 addAttribute 메소드를 통하여 키 벨류 형식으로 변수처럼 값을 넘겨줄 수 있는데 이때 변수명은 members, 값은
 *    생성자로 받아들인 memberService 객체의 getAll메소드를 실행시키고 그 값이 변수값이 되게 된다.
 *    이때 return하는 문자열 값은 어떤 뷰에 이 데이터를 반환할것인지에 대한 내용이다. 그 뷰에 해당하는 경로로 값을 전달한다.
 *
 * 3. form 메소드는 역시 모델을 받아들이며 memberForm이라는 이름의 변수로 MemberForm 객체를 생성하여 전달하는데,
 *    이 모델을 member/create 뷰에 뿌리게 된다.
 *
 * 4. create 메소드는 @ModelAttribute 어노테이션을 통해 모델을 받게 되는데 이 행위는 다음 두가지를 의미하게 된다.
 *       ㄱ. 들어온 HTTP 데이터중에서 memberForm에 맞는 필드들에 자동으로 값을 채워준 MemberForm 객체 생성
 *       ㄴ. 그 객체를 담은 Model을 생성
 *    따라서 결과적으로는 모델을 생성하고 그 모델이 자동으로 HTTP 값을 받아들여 form에 축가해주는 역할을 진행하게 된다.
 *    이후 MemberService의 create 메소드를 호출하여 해당 폼의 name필드와 email필드를 뽑아내어 파라미터로 넣어주어 넘긴다.
 *
 *    여기서가 매우 중요한데 /members로 리다이렉팅을 해주는데 이 행위를 하는 이유는 다음과 같다
 *       ㄱ. 원래는 뷰의 폼을 생성할 때 url은 new로 변동이 없어야 하지만 redirect를 해주게 되면 /members 요청을 날리게 되고 url이 바뀌게 된다.
 *       ㄴ. 이때 get요청을 기본적으로 날리게 되는데 이는 1. 과정을 수행하게 되고
 *       ㄷ. 결과적으로 list 메소드를 실행한것과 같은 결과를 일으키게 된다.
 *    이는 데이터를 넘겨준 후 페이지 새로고침을 진행하였을때 post 요청을 한번 더 날리게 되는(url의 변동이 없으므로) 데이터 중복 전송을 막을 수 있는
 *    효율적인 방법 중에 하나이다.
 */