## 기초 
### 스크립트 작성법
- 외부 스크립트 불러 오기 
```html
<body>
    <script src="script.js"></script>
</body>
```
### 콘솔창 활용
- js는 웹 브라우저에서 실행되는 언어이므로 웹 브라우저의 콘솔창에서 자바스크립트 코드를 바로 작성해 실행할 . 수있다.

### 에러 커뮤니티
- 발생할 수 있는 에러들과 해결 방법 나열되어 있음.
- https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Errors

### 변수 
- let
    - 변수명 중복 불가
    - 호이스팅 불가 (var는 선언이 최상위로 올라온다.)
- const 
    - 상수 할당 

### 형 변환 
- 숫자 + 문자열 => 문자열 연산으로 처리 
- 명시적 형변환
    - String(num)


### 연산 
- 실수 + 실수가 부동소수점으로 계산 된다. 0.1 + 0.1의 결과는 0.2가 아님.
- `===` 값과 자료형이 같으면 true
- `==` 값이 같으면 true 
- x && y : x가 참이면 y를 반환하고, 거짓이면 x를 반환한다.
- x || y : x가 참이면 x를 반환하고, 거짓이면 y를 반환  
-  AND 연산자는 연산 결과가 거짓으로 평가되면 거짓으로 평가된 피연산자를 반환
    ```javascript
    "" && "cat"; // ""
    undefined && "cat"; // undefined
    0 && "cat"; // 0
    null && "cat"; // null
    ```

### 배열 
```javascript
let studentScore = [80,70,80,70];
console.log(studentScore[0]);
```
- 배열은 모든 자료형을 저장할 수 있다. 
```javascript
let array = ['abc', 10, true, undefined, null, [], {}, function(){}];
```

### 객체리터럴
- 객체를 정의하는 가장 간단한 방법
- 중괄호를 사용하며, 키와 값이 한쌍으로 이루어진 속성이 들어간다. 
- 배열과의 차이 
    - 값을 키로 구분한다.
    - 같은 점수 개념이여도 과목별로 다른 점수를 더 직관적으로 확인할 수 있다.
    ```javascript
    let studentScore = {
        koreanScore:80,
        englishScore:90,
        mathScore:100
    };
    console.log(studentScore.koreanScore);
    console.log(studentScore['englishScore']);
    ```

## 자바스크립트 함수 다루기 
### 정의 방법
- 함수 선언문
    - function 식별자(){} 
    - 사용 시, 식별자();
- 함수 표현식
    - 변수에 할당한 함수
    ```javascript
    const 변수명 = function(){}; // 익명함수
    변수명(); // 호출 
    const 변수명 = function 식별자(){}; // 네이밍함수 
    변수명(); // 호출 
    식별자(); // 호출불가
    ```
    - 익명함수 vs 네이밍함수 
        - 우선 익명함수는 무조건 변수에 넣어야 하므로, 호이스팅이 불가능하다.
        - 에러 발생 시 익명함수는 `anonymous function` 이라고 표시되지만 네이밍 함수는 특정한 함수의 이름이 떠서 디버깅이 쉽다.
        - 결론적으로 진짜 간단하게 쓸 방식으로 하는거 아니면 네이밍함수를 쓰자.
    - 화살표함수 vs 명시적(function)함수 
        - this : 화살표는 부모 this를 그대로 같이 씀, 명시적은 자신이 호출된 객체 this
- 화살표 함수 
    ```javascript
    const gugudan = () => {};
    ```
### 함수 기능 확장
- 매개변수와 인수 
    - 매개변수가 있는 함수를 매개변수 없이 호출해도 오류가 나지 않음 (신기하네?)
- return문
    - 조기 return을 사용하면, 함수 결과값을 변수에 담을 경우 그 변수에는 undefined가 들어간다. 

### 함수 호이스팅
- 변수 호이스팅은 var 키워드로 선언한 변수에 한해서 적용된다.
- 이는 함수 호이스팅시에도 마찬가지다.
```javascript
printHello();
function printHello(){
    // success
}
// =====================
printHello();
var printHello = function printHello()  {
    // fail (함수가 정의되지 않음)
}
// ======================
// let, const 키워드로 함수를 선언하면, 호이스팅 자체가 되지 않는다. 
```
### 즉시 실행 함수
- 함수 선언 시, 일반적으로 전역 스코프에 정의되며 프로그램이 종료되기까지 전역스코프에 선언한 함수는 메모리에서 사라지지 않는다.
- 이때 즉시 실행 함수를 사용하면 전역 스코프를 더럽히지 않고 실행할 수 있다.
- 다만 에러시 함수명이 안 떠서 디버깅이 어려울 수 있다.
```javascript
(function init(){
    console.log("init!!");
})  (); 

init(); // ReferenceError: init is not define

(function sum(a,b){
    console.log(a + b);
}) (10,20); //30
```

## 자바스크립트 객체 다루기
### 객체란
- `const person = {};`  : {}을 이용해 생성하는 것은 리터럴 방식으로 객체를 생성했다 표현한다.
- `const person = {"crystal"};`
- ```javascript
    const person = {
        name : "crystal",
        belongings : ["water", "rice", "wallet"],
        age : 26,
        isAdult : true,
        scores : {
            english : 100,
            coding : 50
        },
        printInfo:function(){
            console.log('printInfo');
        }
    };
    ```
- 접근 방법 
```javascript
console.log(person["name"]);  === console.log(person.name); 
console.log(person["age"]);  
console.log(person[name]); // ReferenceError: name is not defined
// 배열
console.log(person["belongings"][0]); // water
// 2차원 객체
console.log(person["scores"]); // { english : 100, coding : 50 }
console.log(person["scores"]["english"]);   // 100

// method
console.log(person["printInfo"]()); // printInfo
```
- 재할당
    - `person.name = 'newCrystal';`
    - 재할당 시, 해당 키가 객체에 없다면 (객체에 없는 속성이라면) 키와 값으로 구성된 새로운 속성이 객체에 추가된다. 
- 객체 속성 동적으로 삭제하기
    - `delete person.name;`

### 다양한 내장 객체
- [String, Array, Date, Math 등](https://thebook.io/080313/0553/)
- [더 다양한 내장 객체](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects)

## 브라우저 객체 모델 사용하기
- 브라우저 객체 모델은 window 부터 시작하며, 그 아래 document, location, history, navigator, screen 등이 있다.

## 문서 객체 모델과 이벤트 다루기
- HTML 문법으로 작성한 태그, 속성, 주석, 텍스트와 같은 구성 요소들은 모두 웹 브라우저에서 각각 하나의 객체로 인식된다.
- 이러한 HTML 구성 요소들을 다루는 객체를 문서 객체 모델 (DOM)이라고 한다.
- 문서 객체 모델은 window 객체의 document 객체를 이용해 직접 조작할 수 있다.


# 기타
## 브라우저 이벤트란?
- 사용자가 버튼을 클릭했을 때, 웹페이지가 로드되었을 때와 같은 것
- Dom 요소와 관련 
- [javaScript Event 종류](https://inpa.tistory.com/entry/JS-📚-이벤트-💯-총-정리)
    - Form Event에는 input이나 textarea 요소 값이 변경되었을 때를 의미하는 `input` 이벤트도 있다.
    - `change` : select box, check box, radio 등의 상태가 바뀌었을 때 
### 적용방법
- lnline 방식
    - `<input type="button" onclick="alert('Hello world');" value="button" />`
    - 이벤트가 발생한 대상을 필요로하는 경우 this를 통해 참조 가능 
        ```jsp
        <input type="button" onclick="alert('Hello world, ' + this.value);" value="button" />
        
        // >> Hello world button
        ```

- script

