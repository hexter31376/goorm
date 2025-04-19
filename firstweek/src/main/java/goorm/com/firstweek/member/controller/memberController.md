# 📘 Spring MVC - MemberController 흐름 정리

## 🎯 역할 요약

- `@Controller`는 웹 요청을 처리하는 **프레젠테이션 계층 컴포넌트**임
- URL 매핑은 `@RequestMapping`, `@GetMapping`, `@PostMapping` 등을 통해 지정
- 비즈니스 로직 호출은 **Service 계층**에 위임하여 **단일 책임 원칙(SRP)**을 지킴
- 뷰 렌더링을 위한 데이터 전달은 `Model`을 통해 수행
- 폼 데이터 수신은 `@ModelAttribute`로 자동 바인딩

---

## 🔧 MemberController 코드 설명

```java
@Controller
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    // 생성자를 통한 의존성 주입
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // [1] 회원 목록 조회 - GET /members
    @GetMapping
    public String list(Model model) {
        model.addAttribute("members", memberService.getAll());
        return "member/list"; // members를 포함한 뷰 반환
    }

    // [2] 회원 등록 폼 - GET /members/new
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "member/create"; // 비어 있는 memberForm 뷰에 전달
    }

    // [3] 회원 등록 처리 - POST /members
    @PostMapping
    public String create(@ModelAttribute MemberForm form) {
        memberService.create(form.getName(), form.getEmail());
        return "redirect:/members"; // PRG 패턴 적용
    }
}
```

---

## 🧠 키워드 설명

### 🔹 `@Controller`
- 해당 클래스가 컨트롤러임을 명시
- 메서드의 `return` 값을 **뷰 이름(view name)**으로 처리

### 🔹 `@RequestMapping("/members")`
- 해당 클래스의 모든 경로 앞에 `/members`가 붙음

### 🔹 `Model model`
- 뷰에 데이터를 넘기기 위한 객체
- `addAttribute("key", value)`로 넘긴 값을 뷰에서 `${key}`로 사용 가능

### 🔹 `@ModelAttribute`
- 요청 파라미터를 객체에 바인딩해주는 어노테이션
- 필드 이름과 폼의 `name` 속성이 일치해야 바인딩됨
- 바인딩된 객체는 자동으로 `Model`에도 등록됨

---

## 🔄 PRG 패턴 (Post-Redirect-Get)

```
POST /members
  ↓
서버 처리 후 redirect:/members 리턴
  ↓
GET /members (list 메서드 호출)
  ↓
최신 회원 목록 보여줌
```

### ✔️ 장점
- 새로고침 시 POST 중복 전송 방지
- URL이 `/members`로 명확하게 정리됨

---

## ✅ 정리 요약표

| 구성 요소         | 역할                                               |
|------------------|----------------------------------------------------|
| `@Controller`     | 컨트롤러 클래스 지정, 뷰 반환 처리                   |
| `@RequestMapping` | URL prefix 설정                                    |
| `@GetMapping`     | GET 요청에 대한 메서드 매핑                         |
| `@PostMapping`    | POST 요청에 대한 메서드 매핑                        |
| `Model`           | 뷰에 데이터 전달 (request scope)                   |
| `@ModelAttribute` | 폼 데이터를 객체로 바인딩 + 모델에 자동 등록         |
| `redirect:/url`   | 응답 후 GET 요청 유도 (PRG 패턴 구현)              |

---

## 📌 추가 참고

- `@RestController`를 사용하면 JSON 반환이 기본이므로 뷰 없이 API 응답 처리
- `@RequestBody`는 JSON 요청 처리, `@ModelAttribute`는 HTML form 처리에 적합
- 컨트롤러는 최대한 단순하게 → 비즈니스 로직은 서비스 계층에 위임하여 관심사 분리

