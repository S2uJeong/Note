# JPA 영속성에 대해 알아보자

- 실제 사례
    - getposts()하며 updateViewCount를 해줘야 하는데 조회라고만 생각해서 @Transactional 누락함
    - find로 찾아서 영속성이 있는데도 불구하고 update하고서 save 호출함 -> 더티 체킹이 있어 불필요한 행동
    - transactional을 메서드 안에 있는 함수에 따로 붙여 줬는데, 전체 적용이 되지 않음 -> 프록시  연관 있어 보였음
    - https://www.notion.so/JPA-c3ee68562ef64a14bc7f6fa64710b493

---

아래와 같이 코드를 작성하면, Transactional이 제대로 동작할까?

```
void test() {        
  updatePostView();        
  System.out.println("hello");   
}      

@Transactional    
private void updatePostView() {       
   post.updatePostView();    
}
```



위와 같이 작성하면 `@Transactional`이 의도한 대로 동작하지 않습니다. `updatePostView()` 메서드에 `@Transactional` 애노테이션이 적용되어 있어도, 이 메서드가 같은 클래스 내의 `test()` 메서드에서 직접 호출되면 트랜잭션이 적용되지 않습니다. Spring의 AOP 기반 `@Transactional`은 외부에서 호출되는 경우에만 프록시를 통해 트랜잭션을 활성화하기 때문입니다.

### 해결 방법

1. **`updatePostView()`를 외부 클래스에서 호출**: `test()` 메서드를 다른 클래스에 두고 `updatePostView()`를 호출하면 `@Transactional`이 정상적으로 작동합니다.
2. **`test()` 메서드에 `@Transactional`을 적용**: 만약 `test()` 메서드 전체에 트랜잭션을 적용해도 된다면, `test()` 메서드에 `@Transactional`을 적용하는 것이 간단한 해결책이 될 수 있습니다.

예를 들어, 아래와 같이 `test()` 메서드에 `@Transactional`을 적용할 수 있습니다.

```
java코드 복사@Transactional
void test() {
    updatePostView();
    System.out.println("hello");
}
```

이렇게 하면 `updatePostView()` 메서드도 `test()` 메서드의 트랜잭션 범위 내에서 실행되므로 정상적으로 트랜잭션이 작동합니다.