# 메시지 기능
- 상황 : 화면에 보이는 문구 변경 요청시, html파일에 하드코딩 되어 있으면 일일이 바꿔줘야함.
- 이럴 때, 다양한 메시지를 한 곳에서 관리하도록 하면 수정이 쉬워짐.

- messages.properties
```properties
item=상품 
item.id=상품 ID
item.itemName=상품명
item.price=가격 
item.quantity=수량
```
- 각 html에선 해당 데이터를 key 값으로 불러온다.
```html
addForm.html
<label for="itemName" th:text="#{item.itemName}"></label>
editForm.html
<label for="itemName" th:text="#{item.itemName}"></label>
```

# 국제화
- 각 나라별 언어로 보이게 서비스할 수 있다.
- messages.properties 파일을 나라별로 관리하는 방법을 이용
- 어떤 나라에서 접근한것인지 HTTP `accept-language`헤더값을 이용할 수 있다. 

# 스프링에서 활용 방법 

## 메시지 
- 스프링이 제공하는 `(I)MessageSource` <- `(C) ResourceBundleMessageSource`를 스프링 빈으로 등록 
- `.setBasenames()` 을 통해 properties 파일을 읽어 사용하며,
- 국제화 기능을 사용하려면 이름 뒤에 언어 정보를 주면 같이 읽는다. 
- 파일 위치는 /resources/ 
- 여러 파일을 한번에 지정할 수 있다.
- 스프링 부트를 사용하면 application.properties 설정에 따라 메시지 소스를 설정할 수 있다. 
    `spring.messages.basename=messages,config.i18n.messages`
  - 스프링부트 메시지 소스 기본값은 `spring.messages.basename=messages`

### MessageSource 인터페이스
```java
public interface MessageSource {
    String getMessage(String code, @Nullable Object[] args, @Nullable String defaultMessage, Locale locale);

    String getMessage(String code, @Nullable Object[] args, Locale locale) throws NoSuchMessageException;
}
```

### CODE
- messages.properties
```properties
hello=안녕
hello.name=안녕 {0}
```
- Test.java
```java
@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null)).isInstanceOf(NoSuchMessageException.class);
    }

    /**
     * 없는 코드 호출 시,
     * 기본 메시지 사용
     */
    @Test
    void notFoindMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null,"기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /**
     * 매개변수 사용
     */
    @Test
    void aegumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }
}
```

## 국제화 파일 선택 
- locale 정보를 기반으로 국제화 파일을 선책한다. 
- Locale이 en_US 의 경우 messages_en_US -> messages_en -> messages 순서로 찾는다.
- 스프링 `LocaleResolver`
  - Locale 선택 방식을 변경할 수 있도록 제공하는 인터페이스
    - 구현체중 `AcceptHeader``LocaleResolver`은 HTTP 헤더 값을 활용한다.
    ```java
        public interface LocaleResolver {
            Locale resolveLocale(HttpServletRequest request);
            void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale);
        }
    ```

### CODE
```java
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");  // message_ko 가 없어서, 기본값인 messages 이용
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }
```