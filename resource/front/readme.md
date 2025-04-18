# 리팩토링
## 기본원칙
- 비지역적인 입력들(자유변수) < 암시적인 입력 방법 < 명시적 입력 방법  (큰게 좋은 방법)
    - 비지역적 입력과 전역 변수를 줄이려면 클래스(적어도 객체) 일부로 함수와 다른 변수(속성)에 가능한 한 잘 정의된 this를 지정하라.
    ```js
    // 나쁜예
    let user = { name: "Sujeong" };
    function greet() {
        console.log(user.name); // 전역 객체 user에 의존한다.
    }
    // this를 이용한 암시적 입력 방법 
    function greet() {
        console.log(this.name); 
    }
    let user1 = { name : "sujeong", greet : greet};
    let user2 = { name : "sujeong2", greet : greet};
    user1.grret() 
    // 하지만 this도 헷갈릴 수 있다. : 어떻게 호출했는지 실행 시점에 결정되기 때문. 
    // 명시적 인자 전달이 더 안전할 때도 있다 -> 객체나 클래스 안에 함수와 상태를 묶고, this를 명확히 쓰는 게 가장 이상적
    ```
    - 비지역적 입력으로 객체 이름을 하드코딩하는 대신 this를 명시적으로 전달할지 아니면 함수 호출에 바인딩할지 선책
- 부가 작용 -> 실제적이고 의미 있는 반환값

### 변수
- 매직넘버, 긴 코드 줄, Dom 선택은 변수에 넣어 놓고 사용

### 문자열
- `+` 로 잇기보다는 백틱과 그 안에 `${tmp | tmp()}` 방법 사용

### 반복문
- for -> forEach -> map 
```js
var arr = [1,2,3];
// forEach
var newArr = [];
arr.forEach(el => {
    newArr.push(el*2);
});
// map 
var newArr = arr.map(el => {
    return el*2;
});
```  
## 리팩토링 함수와 객체 (OOP)
d


# JS 문법 
## 함수
### 호이스팅
- 코드를 실행하기 전에, 변수와 함수 선언이 끌어올려지는 현상
- 함수 선언식 `function fn()` : 완전 호이스팅, 선언 이전에도 사용 가능하다.
- 함수 표현식 `const fn = function()` : 변수만 호이스팅, 값은 undefined
- 화살표 함수 `const fn = () =>` : 함수 표현식과 같다
- 호이스팅을 막고 싶을 때는 언제일까
    - 호이스팅 방식은 의도보다 먼저 실행 되거나, 버그를 찾기 힘들 수 있음
    - 따라서 의도한 시점에서만 동작하도록 하려면 쓴다. 
    - 순서가 중요한 초기화 코드
    - 같은 이름 함수 덮어쓰기 방지
    - 
### this
- 일반 함수: "내가 호출될 때마다 this가 누구인지 다시 정할래!"
- 화살표 함수: "난 this는 상위 함수에 맡길게. 한 번 정하면 끝!"
- 화살표 함수는 자기가 선언된 스코프의 this를 기억해서 고정의 의미를 가진다.
    - 아래와 같은 콜백함수로 쓰인 상황에서 안정적으로 쓸 수 있다.
    ```js
    // 1. 콜백함수에서 사용
    // 1-1. 일반 함수 썼을 때 
    function Timer() {
        this.name = "Sujeong";
        
        setTimeout(function() {
            console.log(this.name);
        }, 1000); // window or undefined
    }
    // 1-2. 화살표 함수 썼을 때
    function Timer() {
        this.name = "Sujeong";
        setTimeout(() => {
            console.log(this.name);
        }, 1000); // Sujeong
    }
    // 2. 주의) 일반적 상황에서는 의도치 않은 결과가 나옴
    const obj = {
        name: "Sujeong",
        sayHello: () => {
            console.log(this.name);
        }
    };
    obj.sayHello(); // undefined or window
    ```

### this를 잃지 않는 방법
```js
// 1. 화살표 함수 사용 : 화살표 함수는 자기 자신한테 this가 없으면 가장 바깥의 this를 그대로 가져와 사용한다.
function Timer() {
    this.seconds = 0;
    setInterval(() => {
        this.seconds++; // Timer 내부에서 그대로 쓰기 때문에 상위인 Timer를 대상으로 this를 명확히 쓰게 됨.
        console.log(this.seconds); 
    }, 1000);
// 2. bind 사용 : this와 객체를 강제 연결
function greet() {
    console.log(this.name);
}
const user = { name: "Sujeong"};
const boundGreet = greet.bind(user);
boundGreet(); 
// 3. this를 변수에 담아둔다 : 옛날 방식이지만 IE도 지원돼서 안전
function Timer() {
    var self = this;
    self.seconds = 0;
    setInterval(function() {
        self.seconds++;
        console.log(self.seconds);
    }, 1000);
}
```

### 값,변수
- false : undefined, null, 0, "" , NaN

# JS OOP
## 기본 
- 활용 방안 : 복잡한 상태/행동이 필요한 경우, 상태+기능 묶어서 재사용하고 싶을 때 
```js
var cat = {
    name : "냥이",
    age : 1
    speak : function() {console.log(this.name)};  // this이용해서 객체 안의 다른 변수 선택
}
cat.speak()
```
```js
function Cat(name, age) {
    this.name = name
    this.age = age
}
Cat.prototype.speak = function() {console.log(this.name)};

var cat = new Cat("이름", 1)
cat.speak()
```

# 모듈 패턴
- 아이디어 : 변수를 즉시실행 함수 안에 가두고, 공개할 것만 return해서 외부 노출을 최소화 한다.
- 활용 방안 : 전역 변수 안 쓰고 관리하고 싶을 때 
```js
var Counter = (function() {
    var count = 0;

    function increment() {
        count++;
    }
    function getCount() {
        return count;
    }
    return {
        increment: increment,
        getCount : getCount
    }
})
Counter.increment();
console.log(Counter.getCount()); // 1
```
```js
const Counter = (function() {
    let count = 0; // private 변수
    return {
        increment: function() {
            count++;
            console.log(count);
        },
        getCount: function() {
            return count;
        }
    };
})();

Counter.increment(); // 출력: 1
Counter.increment(); // 출력: 2
console.log(Counter.getCount()); // 출력: 2
console.log(Counter.count); // ❌ undefined (private 변수)
```

# 성능 + 재사용성 높이기
- 중복 제거 : 공통 기능은 클래스나 모듈로 분리한다.
- 은닉화 : private 변수/메서드는 은닉해서 안정성을 높인다.
- 구조화 : 파일 단위로 기능 쪼갠다.
- 정적 함수는 클래스보단 모듈로 (Math.max 같은 것들)