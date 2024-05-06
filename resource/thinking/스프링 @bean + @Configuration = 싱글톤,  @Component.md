https://mangkyu.tistory.com/75

- 단순히 구현체를 직접 의존 연결하여 사용할 때에는 관계를 맺을 각 구현체(클래스)위에 @Component를 붙이는 것만으로 된다.
- 하지만 우리는 유연한 아키텍처를 위해 인터페이스를 생성한 뒤, 해당 구현체를 이후에 조립하는 방법도 활용할 수 있다.
- 이런 방법을 이용할 때 쓰는 조합이 @Configuration + @Bean 이다.
- @Configuration이 붙은 클래스 안에 조립하는 메서드를 만든 뒤 이 메서드에는 @Bean을 선언하여 스프링빈으로 등록해준다
  ```java
  @Configuration
    public class ResourceConfig {

    @Bean
    public ItemRepository itemRepository() {
        return new ItemRepositoryV1();
        }

    }
   ```
  - 참고로, bean이름은 이때 설정한 메서드 이름이 된다. 
- @Configuration을 붙이지 않고 @Bean을 붙여도 스프링 빈으로 등록이 되나, 싱글톤을 보장 받지 못함을 주의하여야 한다.
    - @Configuration 에는 프록시 패턴이 적용 되기 때문에 

