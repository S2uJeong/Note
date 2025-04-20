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
## 배열과 객체 대안
```
- 배열 대안 : SET, 객체, 비트 필드  
- 객체 대안 : MAP
- 주의점 : SET, MAP은 `.map` 기능이 없어서 사용하기 위해 다시 배열로 전환해야 할 수 도 있음
```
### 배열 대안 : 객체
- 순서가 상관 없을 때
- 동일한 구조에서 여러 유형을 혼합해서 사용하고 싶을 때
- 숫자 인덱스보다 의미 있는 레이블(표식)이 필요할 때
```js
var songs = [["label","chords"]];
// 객체 변환 시, 이렇게 진짜 객체로 선언하면 forEach를 사용할 수 없음 / 의미 : 노래 그 자체 
var songs = {label : "label", chords : "chords"};  // console.log(songs.label);
// 배열을 선언 후, 그 안에 객체를 넣어준다. 그럼 forEach같은 기능도 쓸 수 있게됨. / 의미 : 노래 목록을 담은 배열
var songs = [];
songs.push({label : "label", chords : "chords"});
songs.forEach(song => {
  console.log(song.label, song.chords);
});
```
### 객체 대안 : Map 
- 컨테이너 크기를 쉽게 알고 싶을 때
- 객체와 함께 올 수 있는 계층적 개체들은 원하지 않을 때  
- 서로 비슷한 요소에 대한 컨테이너가 필요할 때
- 일반적으로 컨테이너를 반복하고 싶을 때 
- Map 단점
  - `.get`, `.set` 표기법은 깊게 중첩된 구조를 처리하는 `object.property.etc` 구문처럼 편리하지 않다.
  - 원격 API에서 JSON 데이처를 가져오면 객체로 작업하는 것이 더 쉬울 수 있다.
  - 이러한 이유로 내부 객체는 그대로 남겨 두고, 외부 컨테이너에서만 Map을 사용할 수동 ㅣㅆ다.
```js
var labelCounts = {};
if (Object.keys(labelCounts).includes(label)){
    labelCounts[label] =  labelCounts[label] + 1;
}
Object.keys(labelCounts).forEach(function(label){
   labelProbab[label] = labelCounts[label] / songs.length; 
});

var labelCounts = new Map();
if (labelCounts.has(label)) { // Array.from(labelCounts.keys())
    labelCounts.set(label, labelCounts.get(label)+1);
}
labelCounts.forEach(function(_count, label){ // Map forEach는 값, 키의 순서로 매개변수가 들어가며 `_` 는 함수에 쓰이지 않는 파라미터 의미 
   labelProbab[label] = labelCounts.get(label) / songs.length;
});
```
## 함수 추출
### 절차 코드 제거
- 동시에 실행되어야 하는 함수들이 있다면, 이를 묶어서 또 하나의 함수로 정의해서 사용
### 익명 함수 추출과 이름짓기
```JS
$('.my-button').on('click', function() {
    window.location = "http://www.google.com"
});
$('.other-button').on('click', function() {
  window.location = "http://www.google.com"
});
// 변경 후 
var siteUrl = "http://www.google.com"
function visitSite() {
    window.location = siteUrl;
}
$('.my-button').on('click', visitSite);
$('.other-button').on('click', visitSite);
```
### 하나의 전역 객체로 API 간소화
- 목적 : 전역 변수의 범위 줄이기, 코드가 어떻게 사용될지 결정하기 위한 API 디자인
- 첫번째) 전역 변수들과 함수들을 담을 calssifier 객체를 생성하여 전역 객체에 직접적으로 접근할 수 없도록 한다.
  ```js
  // 특정 함수 내부에 사용된 전역 객체 함수로 실행되는 함수를 객체를 통해 접근하도록 바꾸기
  function trainAll() {
      // setup();
      classifier.setup();
      setDifficulties();
  }
  var classifire = {
      setup:
          this.songs = [];
          this.allChords = new Set();
          ...
      };            
  };
  
  ```
- 두번째) 함수 인라인화 : 많은 역할을 하지 않는 함수에 대해 함수적 역할을 풀고, 객체에 직접 할당하는 것으로 결정
  ```js
  function trainAll() {
      setDifficulties();
  }
  var classifire = {
      this.songs = [],
      this.allChords = new Set(),
  };
  ```
- 세번째) 객체로 추출
  - A 함수가 전역 변수를 정의하기 위한 함수라 하고, 이 함수를 다른 함수 안에서 실행시켜 전역 변수로 쓰인다고 할 때, 이러한 연결 구조는 오류가 발생하기 쉽고 확장하기 어렵다.
  - 이를 방지하기 위해 객체를 만들어 본다.
  ```js
  // 전 
  function setSongs() {
  imagine = ['c','cnmaj7'];
  toxic = ['cm','eb'];
  // 등등 여러 배열 정의
  };
  function trainAll() {
  setSongs();
  setup();
  setDifficalties();
  train(imagine, easy); // setSongs에서 만든 변수가 파라미터로 들어가 있다.
  train(toxic, hard);
  }
  // 후  
  var songList = {
  songs: [],
  addSong: function(name, chords, difficulty) {
  this.songs.push({name: name,
  chords: chords,
  difficulty: difficulty});
  }
  };
  function setSongs() { // songList 객체를 사용하도록 변경
  songList.addSong('imagine', ['c', 'b'], easy);
  songList.addSomg('toxic', ['b','d'], hard);
  };
  function trainAll() {
  setSongs();
  setup();
  setDifficalties();
  songList.songs.forEach(function(song){
  train(song.chords, song.difficulty);
  })
  }
  ```
### 생성자 함수로 새로운 객체 얻기
- 객체로 생성하는 방식과 리터럴 객체 방식 차이
- 리터럴 객체는 새로운 버전의 객체를 얻기 위해 복제, 깊은 복사 등 여러 작업 수행 필요 => NEW 키워드 사용
- 생성자 내부에 비공개 함수와 변수를 가질 수 있고 var,let,const를 사용하지 않고 전역 변수를 선언할 수도 있다. 
```js
// 생성자 함수 사용 법 
const Classifier = function() {
    const SongList = function() {
        this.allChords = new Set();
        this.difficulties = ['easy', 'medium', 'hard'];
        this.songs = [];
        this.addSong = function(name, chords, difficulty) {..};
    };
};
this.songList = new songList();
// ...
const classifier = new Classifier();
```

### 생성자 함수 VS 팩토리 함수 
- NEW 키워드로 객체를 생성하는 방법의 대안으로 `Object.create`를 사용할 수 있다.
- 생성자 함수 안에서 접근할 수 있는 것과 없는 것
  ```js
  const Secret = function(){
    this.normalInfo = 'this is normal'; // public 
    const secret = 'sekrit'; // 함수 내부의 지역 변수 
    const secretFunction = function(){ // 수 내부 변수로만 선언
        return secret;
    };
    this.notSecret = function(){ // this로 인스턴스에 붙은 함수
        return secret;
    }
    totallyNotSecret = "I'm defined in the global scope"; // this도 없는데 const 같은 것도 없어서 전력 스코프에 붙어버림 (window 등)
  };
  const s = new Secret();
  console.log(s.normalInfo); // 'this is normal'
  console.log(s.secret); // undefined
  console.log(s.secretFunction()); // 오류 발생 지점 TypeError: s.secretFunction is not a function
  console.log(s.notSecret()); // 'sekrit'
  console.log(s.totallyNotSecret);  // undefined
  condole.log(totallyNotSecret); // I'm defined in the global scope
  ```
- 위 생성자 함수를 팩토리 함수로 표현
  ```js
  var secretTemplate = (function() {
    var obj = {};
    obj.normalInfo = 'this is normal';
    const secret = 'selrit';
    const secretFunction = function(){
      return secret;
    };
    obj.notSecret = function(){
      return secret;
    };
    totallyNotSecret = "I'm defined in the global scope";
    return obj;
  })();
  
  const s = Object.create(secretTemplate);
  ```
- 모듈 패턴으로 
  ```js
  var secretTemplate = (function() {
      const secret = 'selrit';
      const secretFunction = function(){
        return secret;
       };
      totallyNotSecret = "I'm defined in the global scope";
      return{normalInfo: 'this is normal', notSecret(){
          return secret;
      }};
  })();
  const s = Object.create(secretTemplate);
  ```

- # JS 문법 
## 함수
```js
// 1. 익명 함수 리터럴 방식
function(){};
// 호출하는 유일한 방법은 포함된 코드 자체를 실행하는 것 : 즉시 함수 실행 표현 방식 
(function(){})();
(function(){}());
// 2. 명명된 리터럴 함수
function name(){};
// 3. 변수에 할당된 리터럴 익명 함수
var name = function(){};
```
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