# 04_스프링 MVC - 백엔드 웹 개발 핵심 기술
- https://github.com/S2uJeong/spring-servlet

## 웹 애플리케이션 이해
### 웹 서버, 웹 애플리케이션 서버
- 웹 서비스는 HTTP를 기반으로 거의 모든 형태의 데이터를 전송한다.

- Web Server
  - 정적 리소스 제공
  - NGINX, APACHE
- Web Applicaion Server
  - 웹 서버 기능 + 프로그램 코드를 실행해서 애플리케이션 로직 수행
  - 자바는 서블릿 컨테이너 기능을 제공하면 WAS라 하는 분위기
  - 톰캣, Jetty, Undertow

- 웹 서버와 WAS 혼용 이유
  - WAS만 사용시, 서버 과부하 우려가 있으며 WAS 장애시 오류 화면 노출 불가
    - 웹 서버가 정적 리소스 처리 후, 동적인 처리가 필요하면 WAS에게 요청을 위임 함으로써
        - 효율적인 리소스 관리 (정적리소스/동적 리소스 별 각 서버 증설) 가능
        - 웹 서버는 잘 죽지 않으므로 장애시 오류 화면 제공이 가능

### 서블릿
- 특징
  - urlPatterns의 URL이 호출되면 서블릿 코드가 실행
  - 개발자는 HTTP 스펙을 매우 편리하게 사용할 수 있음
    - HTTP 스펙을 맞추기 위한 코드를 빼고, 서비스 로직만을 코드로 생성 

- 서블릿 컨테이너
  - 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.
  - 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기 관리 
  - 서블릿 객체는 싱글톤으로 관리된다. 
  - JSP도 서블릿으로 변환되어서 사용된다.
  - 동시 요청을 위한 멀티 쓰레드 처리 지원 
    - 요청이 올 때마다 새로운 자바 쓰레드를 생성 -> 쓰레드풀에 있는 것을 사용


## 서블릿
- 서블릿이 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 HTTP 요청 메시지를 파싱해주고, 그 결과를 `HttpServletRequest` 객체에 담아서 제공한다.
- 임시 저장소 기능 : 해당 HTTP 요청이 시작부터 끝날 때 까지 유지 됨
  ```java
  request.setAttribute(name,value)
  request.getAttribute(name)
  ```
- 세션 관리 기능 
  `request.getSession()`
- 

### 주요객체
- HttpServlet
- HttpServletRequest
  - 파라미터 전송 기능
- HttpServletResponse
  - HTTP 응답코드 지정, 헤더 생성, 바디 생성
  - 쿠키 / redirect 편의메서드 

## MVC 패턴
- 비즈니스/뷰 계층을 JSP에서 따로 따로 분리하고자 하는 바람에서 나타난 패턴이다.
  - 계층마다 변경 라이프 사이클이 다름
  - JSP는 렌더딩 특화 기술임. 때문에 이 부분의 업무만 담당하는 것이 효과적임
- 계층별 역할
  - 컨트롤러 : HTTP 요청을 받아서 파라미터 검증하고, 비즈니스 로직을 실행, 뷰에 전달할 결과 데이터를 조회해서 모델에 담는다.
  - 모델 : 뷰에 출력할 데이터를 담아둔다. 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 모델에서 가져오기만 하면 된다.
  - 뷰 : 모델에 담겨있는 데이터를 사용해서 화면을 그린다 (HTML 생성)

### Controller와 서블릿의 차이
- MVC패턴에서 Controller는 일반적으로 Servlet와 비슷한 역할을 한다.
- Servlet은 웹 애플리케이션의 요청과 응답을 처리하는데 중점을 두고
- Controller는 비즈니스 로직과 사용자의 입력을 처리하여 Model의 상태를 변경하고, 적절한 View를 선택하여 응답을 제공하는 역할을 수행
- 따라서, Controller가 좀 더 넒은 의미의 역할을 한다. 

### 컨트롤러-서블릿, 뷰-JSP 인 MVC 패턴
- Model은 HttpServletRequest 객체를 사용
- 컨트롤러 로직과 뷰 로직을 확실하게 분리 
- MVC 컨트롤러의 단점
  - View로 이동하는 코드가 항상 중복 호출된다. 
  ```java
    String viewPath = "WEB-INF/views/members.jsp"; 
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response)
  ```
  - ViewPath 중복
    - prefix : `WEB-INF/views/`
    - suffix : `.jsp`
    - 그리고 jsp가 아닌 다른 뷰로 변경한다면, 전체 코드를 다 변경해야 한다.
  - request, response를 사용하지 않는 곳에서도 dispatcher에 인자로 넣어주어야 한다.
  - 공통 처리가 어렵다 : 메서드화 하여도 해당 함수를 호출하는 것 자체가 중복이다. 
- 단점 해결을 위해, 컨트롤러 호출 전에 먼저 공통 기능을 처리하는 수문장 역할을 하는 패턴이 필요함을 인지
  - 프론트 컨트롤러 패턴 (입구를 하나로!)

### MVC 프레임워크 만들기 
- 프론트 컨트롤러 패턴 특징
  - 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받는다.
  - 프론트 컨트롤러가 요청에 맞는 컨트롤러를 호출
  - 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됨
- V1 
  - FrontServlet 제작
  - 모든 url(요청)이 FrontServlet에 가게 하고, url-ControllerName을 저장한 공간에서 찾아 실행시킴
- V2
  - View 분리  
  - 각 servlet이 View객체를 반환하게 구성 
  - FrontServlet에 view객체의 렌더링 함수를 호출하는 부분을 추가 
- V3
  - 컨트롤러는 HttpServletRequest/Response가 필요 없다. 
  - 요청 파라미터 정보를 자바의 Map으로 대신 넘기도록 하면 컨트롤러가 서블릿 기술을 몰라도 동작할 수 있다. 
  - request객체를 Model로 사용하는 대신에 별도의 Model 객체를 만들어서 반환하면 된다. 
  - 서블릿 (기술) 종속성을 없앨 수 있다!
  - 변경내용 
    - 컨트롤러는 뷰의 논리 이름을 반환하게 한다. (ModelView로 감싸서 반환)
    - `/WEB-INF/views/new-form.jsp  -> new-form`
    - 뷰 리졸버 : 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경 
- V4
  - 쉽게 사용 가능하게 Controller가 ModelView가 아닌 ViewName만 반환하게 한다. 
  - 모델을 파라미터로 전달하여, 모델 생성을 따로 안 해도 된다. 
- V5 
  - 어댑터 패턴을 사용해서 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경
  - 핸들러 : 컨트롤러보다 넓은 범위, 어댑터 덕분에 컨트롤러 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리 가능
  - 프론트 컨트롤러 대신 어댑터가 컨트롤러를 호출
  - HandlerAdapter - boolean supports(Object handler) 는 handler가 Controller 개념 같은게 들어오면 instensof()같은 함수를 통해 처리 가능한 Controller를 찾아 반환한다. 
- 이후 어댑터 패턴에 애노테이션을 사용해서 컨트롤러를 더 편리하게 사용할 수 있게 되는 것 

## 스프링 MVC
### 구조 이해 
- `FrontController` 역할이 스프링에선 `DispatcherServlet`
  -  `DispatcherServlet` -> `FrameworkServlet` -> `HttpServletBean` -> `HttpServlet`
  - 요청흐름 
    1. 서블릿 호출, HttpServlet.Service() 호출
    2. FrameworkServlet.service() 시작으로 여러 메서드가 호출
    3. DispatcherServlet.doDispatch() 호출
- DispatcherServlet.doDispatch() 
  1. 핸들러 조회
  2. 핸들러 어댑터 조회
  3. 핸들러 어댑터 실행 
  4. 핸들러 어댑터를 통해 핸들러 실행
  5. ModelAndView 반환 
  6. 뷰 렌더링 호출
     7. 뷰 리졸버를 통해 뷰 찾기
     8. 뷰 반환 
     9. 뷰 렌더링 
- 핸들러 매핑, 어댑터 
  - 스프링 빈의 이름으로 핸들러를 찾을 수 있는 핸들러 매핑
  - 찾은 핸들러를 실행 할 수 있는 핸들러 어댑터를 찾고 실행시키는 것
  - 스프링이 해당 내용을 대부분 구현해두었다.
    - 핸들러 매핑 : 애노테이션 기반 -> 스프링 빈의 이름으로 핸들러 찾음
    - 핸들러 어댑터 : 애노테이션 기반 -> HttpRequestHandler 처리 -> Controller 인터페이스 
- 뷰 리졸버
  - 스프링부트는 InternalResourceViewResolver 라는 뷰 리졸버를 자동 등록하며, 이때 application.properties 설정정보를 사용해서 등록한다.
    ```properties
          spring.mvc.view.prefix=/WEB-INF/views/
          spring.mvc.view.suffix=.jsp
    ```
### 스프링 MVC
- @RequestMapping - 애노테이션을 활용한 유연하고 실용적인 컨트롤러를 만듬
- 핸들러 매핑 : RequestMappingHandlerMapping은 스프링 빈 중에서 @Controller 가 클래스 레벨에 붙어 있는 경우에 매핑 정보로 인식한다. 
- 스프링이 제공하는 ModelAndView 를 통해 Model 데이터를 추가할 때는 addObject() 를 사용
  - 이 방식도, Model을 도입하여 String을 반환하고 Model 객체를 통해 뷰에 그릴 data를 전달 , model.addAttribute() 사용 

## 기본 기능