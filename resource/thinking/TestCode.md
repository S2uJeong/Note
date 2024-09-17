# TestCode를 짜며 고려할 점

- @SpringBootTest 는 통합 테스트이므로, 속도가 빠르고 디버깅이 더 쉬운 단위테스트를 중점으로 알아보도록 한다.

## 공통 기능

- Class 단위

    - `@ExtendWith(MockitoExtension.class)`
        - Mock 관련 객체 (`injectMock` `Mock`)에 대한 초기화를 담당한다.
        - Junit5에 Mockito 관련 기능을 통합하여 사용하며 Junit이 관리하는 테스트 생명주기에 맞춰 Mock을 관리해준다.
        - 이전 Junit4에서는 RunWith을 통해 따로 명시해줘야 했다.
        - Class 선언 위에 애노테이션으로 선언해준다.

- 필드

  ```java
      @InjectMocks
      private AuthController target;
      @Mock
      private MemberService memberService;
  
      private MockMvc mockMvc;
      private Gson gson;
  ```

    - Mock 관련 애노테이션 : 가짜 객체를 만들어 깊은 의존성을 가진 객체에 대한 테스트를 쉽게 만들어 준다.
    - MockMvc :
    - Gson : 객체 직렬화/역직렬화. spring에선 Jackson Mapper를 쓰는게 더 낫다. 그게 표준이니까



## Controller 단

### @WebMvcTest 사용 고민

- `@WebMvcTest`는 `AuthController`와 관련된 **Controller, Exception Handler, Filter 등**과 같은 **Spring MVC 관련 빈**만 로드합니다.

- 일부 Spring context를 로드 한다.

  **전체 Spring 컨텍스트**를 로드하지 않는다.  이는 보안 설정이나 특정 필터 등, 컨트롤러 외부에서 관리되는 **기타 빈**이 필요할 때 문제가 될 수 있습니다.

- **장점**: **`MockMvc`** 설정이나 컨텍스트 로드가 필요 없으므로, 성능이 더 빠르고 테스트 작성이 간단합니다.

  **단점**: HTTP 요청-응답 구조는 테스트하지 않기 때문에, 실제 API 요청 시 발생할 수 있는 **Spring MVC 레이어**의 문제를 잡을 수 없습니다.

  (하지만 MvcMock을 선언해서 사용하게 되면 해당 내용은 해당 없어짐)

- Q.  근데 순수 unitTest에 MvcMock을 필드에 선언해놓고 그 기능을 쓴다면 스프링컨텍스트가 부팅되는거 아냐? 그럼 @WebMvcTest를 쓰는 것과 속도가 비슷해 지는거 아냐?

    -  **MockMvc** 자체는 **Spring 컨텍스트 부팅과는 직접적인 연관이 없습니다**. 즉, **MockMvc**를 필드에 선언해놓고 사용한다고 해서 **Spring 컨텍스트가 부팅되지 않습니다**.
    -  그러나 **MockMvc**를 사용하는 방식에 따라 **Spring 컨텍스트 부팅 여부**가 달라질 수 있습니다. 특히, **Spring의 기능(예: `@WebMvcTest` 등)**을 통해 **MockMvc**를 설정할 때는 Spring 컨텍스트를 부팅하게 됩니다.

  ```java
     // Spring 컨텍스트 부팅 X
     private MockMvc mockMvc;
  
      @InjectMocks
      private AuthController authController;
  
      @BeforeEach
      public void setup() {
          MockitoAnnotations.openMocks(this);
          mockMvc = MockMvcBuilders.standaloneSetup(authController).build();  
      }
  ```

  