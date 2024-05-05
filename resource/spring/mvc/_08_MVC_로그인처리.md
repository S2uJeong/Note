- 로그인 요구사항
  - 홈 화면 - 로그인 전
    - 회원 가입
    - 로그인
  - 홈 화면 - 로그인 후
    - 본인 이름 (누구님 환영합니다.)
    - 상품 관리
    - 로그 아웃
  - 보안 요구사항
    - 로그인 사용자만 상품에 접근하고, 관리할 수 있음
    - 로그인 하지 않은 사용자가 상품관리에 접근하면 로그인 화면으로 이동
```java
@PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //로그인 성공 처리 TODO
        return "redirect:/";
    }
```
# 로그인 - 쿠키, 세션
## 쿠키
- 쿠키종류 
  - 영속 쿠키 : 만료 날짜를 입력하면 해당 날짜까지 유지
  - 세션 쿠키 : 만료 날짜를 생략하면 브라우저 종료시 까지만 유지
### Code
- 로그인
  - Controller단의 메서드에 HttpServletResponse를 파라미터에 추가하여 안에 Cookie 값을 추가해 넘긴다.
  ```java
  @PostMapping("/login")
      public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
          // 위와 동일한 로그인 로직
          // 로그인 성공 처리 TODO
          // 쿠키 활용
          Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
          response.addCookie(idCookie);
          return "redirect:/";
      }
  ```
- 로그아웃 
  - 세션 쿠키방법으로 로그인처리 하였으므로, 서버에서 해당 쿠키의 종료 날짜를 0으로 지정하여 로그아웃처리 한다.
  ```java
  @PostMapping("/logout")
  public String logout(HttpServletResponse response) {
     expireCookie(response, "memberId");
     return "redirect:/";
  }
  private void expireCookie(HttpServletResponse response, String cookieName) {
     Cookie cookie = new Cookie(cookieName, null);
     cookie.setMaxAge(0);
     response.addCookie(cookie);
  }
  ```
### 쿠키의 보안문제
- 쿠키 값은 임의로 변경될 수 있다.
  - 클라이언트가 쿠키를 강제로 변경하면 다른 사용자가 된다.
- 쿠키에 보관된 정보는 훔쳐갈 수 있다.
  - 쿠키 정보가 로컬 PC에서 털릴 수도 있고, 네트워크 전송 구간에서 털릴 수도 있다.
- 해커가 쿠키를 한번 훔쳐가면 평생 사용할 수 있다.
- 대안
  - 쿠키에 중요 값 노출하지 않기
  - 사용자 별로 예측 불가능한 임의의 토큰(랜덤 값)만을 노출하고, 서버에서 토큰과 사용자 id를 매핑하여 인식하고 서버에서 토큰을 관리 
  - 토큰은 해커가 임의의 값을 넣어도 찾을 수 없도록 예상 불가능해야 한다.
  - 해커가 토큰을 털어가도 시간이 지나면 사용할 수 없도록 서버에서 해당 토큰의 만료시간을 짧게 유지
  - 해킹이 의심되면 서버에서 해당 토큰을 강제로 제거하면 되게끔
  -> 결론 : 세션 동작 방식 활용
## 세션
- 중요한 정보는 서버에 저장해야 한다. 서버에 중요한 정보를 보관하고 연결을 유지하는 방법을 세션이라 한다. 
### 과정
1. 사용자가 id,pw 정보를 전달하면 서버에서 해당 사용자가 맞는지 확인한다.
2. 세션 ID 생성 및 value 지정(member)
3. 세션 저장소에 보관
4. 세션 ID만을 쿠키로 전달 
5. 이후 클라이언트 쿠키값에 있는 세션ID를 서버가 저장소 내용과 대조하여 회원 정보를 가져옴 
### Code
- 세션 생성 
  - sessionId 생성
  - 세션 저장소에 sessionId와 보관할 값 저장
  - sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
- 세션 조회
  - 클라이언트가 요청한 sessionId 쿠키의 값으로, 세션 저장소에 보관한 값 조회
- 세션 만료
  - 클라이언트가 요청한 seeeionId 쿠키의 값으로, 세션 저장소에 보관한 sessionId와 값 제거
```java
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {
        // 세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }
    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) {
            return null;
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }
}
```
### 서블릿이 지원하는 세션
- 서블릿은 세션을 위해 `HttpSession` 이라는 기능 제공
- 위에서 세션을 이용한 로그인 처리 코드를 이미 구현해놓았다. (SessionManager)
- 
# 로그인 - 필터, 인터셉터

