# 검증 
- 조건(요구사항)    
  - 입력 사항별 검증 오류 발생시, 입력 폼을 다시 보여준다.
  - 검증 오류를 고객에게 친절하게 안내해서 다시 입력하게 유도
  - 검증 오류 발생해도 고객이 입력한 데이터가 유지되어야 한다. 
  
## 직접 처리
### 문제점
- 뷰 템플릿에 중복처리가 많음
- 타입 오류 처리 불가능 : Controller에 진입하기도 전에 예외가 발생하여, 400 예외가 발생한다. Controller까지 온다 해도, 바인딩이 되지 않는다.
- 결국 고객이 입력한 값을 어딘가에 별도로 관리해야한다. 

### CODE
- controller.java
```java
 @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {
        // 검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
            errors.put("price", "가격 범위가 틀렸습니다.");
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999까지 허용합니다.");
        }
        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
            }
        }
        // 검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        // 성공로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }
```
- addForm.html
```html

<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <link th:href="@{/css/bootstrap.min.css}"
          href="../css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            max-width: 560px;
        }
        .field-error {
            border-color: #dc3545;
            color: #dc3545;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="py-5 text-center">
        <h2 th:text="#{page.addItem}">상품 등록</h2>
    </div>
    <form action="item.html" th:action th:object="${item}" method="post">
        <div th:if="${errors?.containsKey('globalError')}">
            <p class="field-error" th:text="${errors['globalError']}">전체 오류 메시
                지</p>
        </div>
        <div>
            <label for="itemName" th:text="#{label.item.itemName}">상품명</label>
            <input type="text" id="itemName" th:field="*{itemName}"
                   th:class="${errors?.containsKey('itemName')} ? 'form-control
field-error' : 'form-control'"
                   class="form-control" placeholder="이름을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('itemName')}"
                 th:text="${errors['itemName']}">
                상품명 오류
            </div>
        </div>
        <div>
            <label for="price" th:text="#{label.item.price}">가격</label>
            <input type="text" id="price" th:field="*{price}"
                   th:class="${errors?.containsKey('price')} ? 'form-control
field-error' : 'form-control'"
                   class="form-control" placeholder="가격을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('price')}"
                 th:text="${errors['price']}">
                가격 오류
            </div>
        </div>
        <div>
            <label for="quantity" th:text="#{label.item.quantity}">수량</label>
            <input type="text" id="quantity" th:field="*{quantity}"
                   th:class="${errors?.containsKey('quantity')} ? 'form-control
field-error' : 'form-control'"
                   class="form-control" placeholder="수량을 입력하세요">
            <div class="field-error" th:if="${errors?.containsKey('quantity')}"
                 th:text="${errors['quantity']}">
                수량 오류
            </div>
        </div>
        <hr class="my-4">
        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit"
                        th:text="#{button.save}">저장</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/validation/v1/items}'|"
                        type="button" th:text="#{button.cancel}">취소</button>
            </div>
        </div>
    </form>
</div> <!-- /container -->
</body>
</html>
```

## 스프링 - 검증 : BindingResult
- 스프링이 제공하는 검증 오류를 보관하는 객체
- 이 객체가 있으면 `@ModelAttribute`에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출된다.
- BindingResult 는 Model에 자동으로 포함된다. 
```java
/**
 * Ver1.  BindingResult 사용 
 * BindingResult은 꼭 @ModelAttribute 뒤에 위치할 것
 *
 * 필드 오류 : new FieldError
 * 글로벌 오류 : new ObjectError
 */
@PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(!StringUtils.hasText(item.getItemName())){
        bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수입니다."));
        }
        
        if(resultPrice< 10000){
        bindingResult.addError(new ObjectError("item","가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = "+resultPrice));
        }
    }
```
### FieldError, ObjectError, rejectValue(), reject()
- 사용자 입력 오류 메시지가 화면에 남도록 `FieldError`, `ObjectError` 을 이용할 수 있다. 
  - 템플릿 뷰 기능이 정상 상황에선 모델 객체 값을 사용하지만, 오류가 발생하면 FieldError나 ObjectError에 보관된 값을 사용해서 출력한다.
  - BindingResult 는 어떤 객체를 대상으로 검증하는지 target을 이미 알고 있다고 했다. 따라서 target(item)에 대한 정보는 없어도 된다.
  - 해당 객체를 생성하지 않아도 `rejectValue()`, `reject()` 기능을 사용하여 코드를 더 단순화 할 수 있다.
  ```java
  /**
  * Ver2. 사용자 입력값을 유지하는 FieldError, ObjectError 생성자 이용
  * (1) 파라미터 : bindingFailure - 타입 오류 같은 바인딩이 실패했는지 여부
  * (2) .rejectValue() 이용 -  rejectedValue : 오류 발생 시 사용자 입력값 저장하는 필드 
  */
  if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
  
  if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
         bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000},
         null);

   ```
### 메시지 체계적으로 다루기 
- FieldError 생성자
  ```java
  public FieldError(String objectName, String field, String defaultMessage);
  public FieldError(String objectName,  // 오류가 발생한 객체 이름 
                    String field,  // 오류 필드 
                    @Nullable Object rejectedValue, // 사용자가 입력한 값 (거절된 값)
                    boolean bindingFailure,  // 타입 오류 바인딩 실패인지, 검증 실패인지 구분 값
                    @Nullable String[] codes, // 메시지 코드 
                    @Nullable Object[] arguments,  // 메시지에서 사용하는 인자
                    @Nullable String defaultMessage) // 기본 오류 메시지 
  ```
  - 해당 파라미터들을 이용해서, errors.properties를 따로 만들어 관리하는 방법을 사용할 수 있다.
  ```java
  //range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
  new FieldError("item", "price", item.getPrice(), false, new String[]
  {"range.item.price"}, new Object[]{1000, 1000000}
  ```
- 오류 코드 자동화 
  - `rejectValue()` , `reject()`를 사용하여 `FieldError` , `ObjectError`를 직접 생성하지 않고, 깔끔하게 검증 오류를 다룰 수 있다. 
  ```java
  @PostMapping("/add")
  public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
     if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
         bindingResult.rejectValue("price", // 오류 필드명
                                   "range", // 오류 코드 - messageResolver를 위한 오류 코드 
                                   new Object[]{1000, 1000000}, // 오류 메시지에서 {0}을 치환하기 위한 값
                                   null); // 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지 
     }
         // 전체 예외
     if (resultPrice < 10000) {
         bindingResult.reject("totalPriceMin", new Object[]{10000,resultPrice}, null);
     }
  }
  ```
  - 바로 이전 방법처럼 `{"range.item.price"}`표현을 통해 MessageSource를 찾아 메시지를 조회하는 과정이 빠졌는데 어떻게 메시지가 표현될까?
    - BindingResult 는 어떤 객체를 대상으로 검증하는지 target을 이미 알고 있다 따라서, item에 대한 정보 없이 필드명만으로 알아서 찾는다.
    - FieldError() 를 직접 다룰 때 : 오류 코드를 `range.item.price`와 같이 모두 입력
    - `rejectValue()` 사용 : `range`로 간단히 입력 

- MessageResolver 이해
  - 오류 메시지 코드를 단순하게 만들면 범용성이 좋아지고, 자세하게 만들면 범용성이 떨어진다.
  - 이를 자유자제로 활용하기 위해, 메시지에 우선순위를 만들어 사용한다.
  ```properties
   #Level1
   required.item.itemName: 상품 이름은 필수 입니다.
   #Level2
   required: 필수 값 입니다.
  ```
  - 물론 이렇게 사용하려명 객체명과 필드명을 조합한 메시지가 있는지 우선 확인하고, 없으면 좀 더 범용적인 메시지를 선택하도록 추가 개발이 필요하다. 
  - 스프링은 MessageCodesResolver 라는 것으로 이러한 기능을 지원한다.
- 스프링이 직접 만드는 오류 메시지
  - 주로 타입 정보가 맞지 않을 때 발생하는 오류에 대한 메시지 코드가 입력되어 있다. -`typeMismatch`
  - MessageResolver를 거치며 메시지 코드가 생성된다. 

### Validator 분리 
- 복잡한 검증 로직을 별도로 분리 하자.
- ItemValidator.java
  ```java
  import org.springframework.validation.*;
  
  @Component
  public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
     return Item.class.isAssignableFrom(clazz);
   }
    @Override
    public void validate(Object target, Errors errors) {
     Item item = (Item) target;
     ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName","required");
     if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000},null);
        }
    }
  }
  ```
   - `boolean supports(Class<?> c)` : 해당 검증기를 지원하는 여부 확인
   - `validate(Object o, Errors e)` : 검증 대상 객체와 BindingResult
- 호출방법
  (1) 직접 호출 - ItemController.java
  ```java
  private final ItemValidator itemValidator;
  @PostMapping("/add")
  public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) { 
    itemValidator.validate(item, bindingResult);
  
    if (bindingResult.hasErrors()) {
      log.info("errors={}", bindingResult);
      return "validation/v2/addForm";
   }
    // 성공로직 
  }
  ```
 (2) 스프링 `WebDataBinder` 검증기 사용 - ItemController.java
 ```java
  @InitBinder  // 해당 컨트롤러에만 영향을 주는 검증기 
  public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
   }
  @PostMapping("/add")  // @Validated를 검증 대상 앞에 붙여줌으로써 사용 - WebDataBinder 에 등록한 검증기를 찾아서 실행한다.
  public String addItemV6(@Validated @ModelAttribute Item item, BindingResult
          bindingResult, RedirectAttributes redirectAttributes) {...}
 ```
 - `@Validated` : 스프링 전용 검증 애노테이션,  `@Vaild` : 자바 표준 검증 애노테이션

## 스프링 - 검증 : Bean Validation
- 특정 필드에 대한 검증 로직은 대부분 빈 값인지, 특정 크기를 넘는지와 같은 매우 일반적인 로직이 반복된다. 
- 이런 로직을 애노테이션 하나로 공통화, 표준화 한 것이 Bean Validation이다. 
  ```java
  public class Item {
    private Long id;
    @NotBlank // 빈값 + 공백만 있는 경우를 허용하지 않는다.
    private String itemName;
    @NotNull // null을 허용하지 않는다.
    @Range(min = 1000, max = 1000000) // 해당 범위 안의 값이어야 한다.
    private Integer price;
    @NotNull
    @Max(9999) // 최대값만 지정 
    private Integer quantity;
    //...
  }
  ```
### 순수 Bean Validation
- 의존관계 추가
  `implementation 'org.springframework.boot:spring-boot-starter-validation'`
- 검증기 생성
  ```java
  ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  Validator validator = factory.getValidator();
  ```
- 검증기 실행
  ```java
  Set<ConstraintViolation<Item>> violations = validator.validate(item); // ConstraintViolation이라는 검증오류가 비어있으면 오류가 없는 것 
  ```
  
### 스프링 Bean Validation
- 필요 없는 코드 - ItemValidator
  ```java
    private final ItemValidator itemValidator;
    
    @InitBinder
    public void init(WebDataBinder dataBinder) {
     log.info("init binder {}", dataBinder);
     dataBinder.addValidators(itemValidator);
    }
  ```
  - 그럼 검증 로직이 담긴 ItemValidator 파일 없이 어떻게 검증하는 것일까. -> Item.java에 붙은 애노테이션으로.
- 검증 순서
  1. `@ModelAttribute` 각각의 필드에 타입 변환 시도
     2. 성공하면 다음으로
     3. 실패하면 `typeMismatch` 로 `FieldError` 추가
  4. `Validator` 적용 
  - BeanValidator는 바인딩에 실패한 필드는 BeanValidation을 적용하지 않는다. 
- 오류 메시지 설정 
  - BeanValidation 적용 후, bindingResult에 등록된 검증 오류 코드를 보면, 오류 코드가 애노테이션 이름으로 등록된다.
  ```
  @NotBlank
  NotBlank.item.itemName
  NotBlank.itemName
  NotBlank.java.lang.String
  NotBlank
  
  // errors.properties
  errors.properties
  NotBlank={0} 공백X  
  ```
  - 메시지 찾는 순서
    1. 생성된 메시지 코드 순서대로 messageSource에서 메시지 찾기
    2. 애노테이션의 message 속성 사용
    3. 라이브러리가 제공하는 기본 값 사용 -> "공백일 수 없습니다."
    ```java
    @NotBlank(message = "공백은 입력할 수 없습니다.")
    private String itemName;
    ```
  - ObjectError 관련 처리 
    - 기존방법대로 로직을 자바코드로 Controller단에 직접 추가해 주는 것을 권장.

### Bean Validation groups 기능 , 폼 객체 분리 
- 데이터를 등록할 때와 수정할 때는 요구사항이 다를 수 있다. 
  - ex) 등록시에는 id에 값이 없어도 되지만, 수정시에는 id값이 필수이다.
- Bean Validation groups은 동일한 모델 객체를 등록할 때와 수정할 때 각각 다르게 검증하는 방법이다. 
- 하지만 이 기능은 실무에서 잘 사용 안한다. 이유는 등록용 폼과 수정용 폼 객체를 분리해서 사용하는 경우가 많기 때문이다. 
- 폼 객체 분리
  - HTML Form -> Item -> Controller -> Item -> Repository
  - HTML Form -> ItemSaveForm -> Controller -> Item 생성 -> Repository
  - 기존의 Item에 써주었던 애노테이션들을 삭제하고, 따로 만든 Form객체마다에 알맞은 검증 조건의 애노테이션을 붙여준다. 
  ```java
  /**
  * 사용 시, 타입체크를 해주는 @Validated 뒤에 @ModelAttribute()는 도메인 객체를 이용하고, 그 뒤에 form 객체를 따로 사용 
  * @ModelAttribute()에 From객체를 넣으면, 해당 객체가 MVC Model에 담기기 때문에 뷰 템플릿에 접근하는 이름을 함께 변경해주어야 한다. 
  */
  @PostMapping("/add") 
  public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
  }
  ```
  
### Bean Validation - HTTP 메시지 컨버터
- @Valid , @Validated 는 HttpMessageConverter ( @RequestBody )에도 적용할 수 있다.
```java
@PostMapping("/add")
 public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
           return bindingResult.getAllErrors();
        }
        return form;
   }
```
- API의 경우 3가지 경우를 나누어 생각해야 함
  - 성공 요청 : 성공
  - 실패 요청 : JSON을 객체로 생성하는 것 자체를 실패 - 타입에러, 컨트롤러 자체가 호출되지 않음 400 에러 발생
  - 검증 오류 요청 : JSON을 객체로 생성 성공, 검증을 실패 - ObjectError 와 FieldError 를 반환되면 개발자가 필요한 데이터만 뽑아 스펙 정의

- @ModelAttribute vs @RequestBody
  - @ModelAttribute 는 각각의 필드 단위로 세밀하게 적용, 특정 필드에 타입이 맞지 않는 오류가 발생해도 나머지 필드는 정상 처리할 수 있음
  - HttpMessageConverter는 전체 객체 단위로 적용, API 예외처리 중요