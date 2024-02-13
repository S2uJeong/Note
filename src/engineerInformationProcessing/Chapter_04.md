# 📁 프로그래밍 언어

### 📒키워드 : 변수명 작성 규칙, 연산자, printf, 다중 if문, for문, while문, 배열, 포인터, 구조체, C언어의 라이브러리

### **01. 변수**

- 종류
    - 자동변수 auto : 메모리(스택)/일시적/지역적 , 초기화 안하면 쓰레기값이 저장된다.
    - 외부변수 extern : 함수가 종료된 뒤에도 값이 소멸하지 않음, 초기화 안하면 0으로 자동 초기화
    - 정적변수 static :	내부/외부 정적변수로 나뉨, 두 변수 모두 함수가 종료되도 소멸하지 않음. 초기화는 변수 선언 시 한번만 가능하며 생략하면 자동으로 0으로 초기화
    - 레지스터변수 register : 레지스터 용량 부족하면 자동변수도 취급됨, 메모리 주소를 가지지 않아 주소를 구하는 연산자 사용불가

### **02. 데이터 입출력**

**scanf, scanner**

---

- scan(서식문자열, 변수의 주소)
    - 변수 주소 : {&변수}
    - 배열명은 그 자체로 주소를 나타내므로 `&` 를 붙이지 않는다.

    ```c
    printf("%d %f",&i,&j); 
    ```

- %d, o, x : 정수형 10,8,16진수

  %ld,lo,lx : lonn형 “

  %c, s: 문자, 문자열

  %f : 실수형

- Scanner

    ```java
    Scanner sc = new Scanner(System.in);
    int num = sc.nextInt();
    ```

    - nextLine() : 라인 전체를 문자열로 반환
    - nextFloat()
- 오답노트

    ```java
    // 1. 소수점을 포함하여 앞에 4자리
    scanf("%4f", &i)   // 12.123
    // out : 12.1  
    
    // 2. 공백이 있으면 공백 앞까지만 문자열
    char b[8];
    scanf("%s", b) // GIL BUT
    // out : GIL 
    
    // 3. scanf의 같은 명령줄에 들어간 변수는 내용을 공유한다. 
    char b[8], c[8];
    scanf("%c %5c", b,c); // LOVE ME
    // out : b = L , c = OVE M
    
    // 4. 각각 변수는 자신의 형식에 맞는 데이터만 담는다.
    // 5. 실수형에 정수를 담으면 .0으로 저장되지 않고 그냥 정수로 저장된다. 
    
    // 6. 형식에 특정 문자를 기준화할 수 있다.
    scanf("%3d$$%3f",&i,&j);  // 123$$456789
    	// out : i = 123 , j = 456 , 입력한 데이터에 $$가 없으면 정상적 입력 불가.
    ```


**printf**

---

- `scanf`와 달리 주소연산자를 붙이지 않는다.

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/5cc8e934-e4c1-4f75-95ac-d8a614b47a44/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/869da3f3-69a1-4e7c-8e86-ef7ca5f3a9c8/Untitled.png)

1. `%n`출력할 값이 지정한 자릿수보다 큰 경우 자릿수를 무시하고 모두 출력 (소수점 숫자 말고, 정수형 조건일때)
2. VV2543
    1. 기본 오른쪽부터 출력, `-` 붙으면 왼쪽부터 출력
    2.      VVnnnnn ,            nnnnnVV
3.  245.2555

→ 245.255500 — `%f`  소숫점 부분 기본값 6자리 출력

1. 245.255 → 245.256
    1. 소숫점 이하  반올림해서 버린다.
2. vvvvv245.25
    - `%nf`  : **소수점까지** 전체 자릿수, `%.nf` : 소수점 자리만
3. `.3s` - 왼쪽을 기준으로 3글자만 출력  help me ⇒ hel
4. `%8.6s` VVhelp m
5. `%-8.6s` helo mVV
6. 입력 : `scanf(”%2d %3d", &i, &j)` 12345678

   출력 : `printf("%d %d",i,j)`

   (i)12 (j)345

   🔴 주의점 : print시엔 자릿수 무시하고 다 출력하지만, 입력시에는 표시된 숫자만큼만 입력 받으므로 헷갈리지 말것

   내 오답 : (i) 12345678 (j) 12345678


**기타 입출력**

---

- 입력
    1. a = `getchar()` : 키보드로 한 문자를 입력
    2. `gets(a)` : 키보드로 문자열 입력 , enter 기준으로 문자열 하나로 인식
- 출력
    1. `putchar(a)` : 인수로 주어진 한 문자 출력
    2. `puts(a)` : 인수로 주어진 문자열 출력뒤 커서 다음 줄로 이동

  ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/01de8af3-ca5c-485d-9f2d-92896b6efab2/Untitled.png)


### 03. 반복문

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/2ccad261-d684-4aab-8242-bb318e8b41eb/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/2f664357-8209-4eb2-9fef-a6d6440d5428/Untitled.png)

### 04. 배열과 문자열

- C언어에서 배열 위치를 나타내는 첨자 없이 배열 이름을 사용하면 배열의 첫 번째 요소의 주소를 지정하는 것과 같다.
- 배얼의 개수보다 적은 수로 배열을 초기화하면 나머지 요소에는 0이 입력된다. (int 배열일 경우)
- C언어에는 문자열을 저장하는 자료형이 없기 때문에 배열 또는 포인터를 이용하여 처리한다.

    ```c
    char arr[] = "문자열입니다."
    chhar arangeArr[5] = "love"   // love/0 으로 5개의 배열이 생성됨. 
    ```

    - 이 경우, 문자열의 끝을 알리기 위한 널 문자 `/0` 가 문자열 끝에 자동으로 삽입. → 따라서 고려하여 배열크기를 저장해야 한다.
    - 이미 선언된 배열에는 문자열을 저장할 수 없어, 선언 시 꼭 초기화 해주어야 한다.
- `strcat(s,s)` : 앞 문자열에 뒤 문자열을 붙이는 함수
- 아스키코드
    - {65:A} , {97:a}
    - char형 변수에는 int가 저장된다.

### 05. 포인터

- {포인터 : 변수의 주소}, {포인터변수 : 변수의 주소를 저장할 때 사용하는 변수}
- 포인터변수는 동적 메모리 영역인 Heap 영역에 접근하는 동적 변수이다.

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/877cceb8-5e58-41f2-92d7-72f9208a4401/Untitled.png)

- `*`
    - 선언할 때 - 이 변수가 포인터 변수임을 의미
    - 아닐 때 - 그 포인터변수가 가리키는 곳의 ‘값’을 의미
- 포인터변수
    - `*` 있을 때 - 값을 가짐
    - 없을 때 - 주소를 가짐 → `&`다른변수

```c
int *b; int a;

b = &a;  // 주소를 담는건 데이터형을 지정해 줄 수 없으니까.
*b = a;

main() {
 int a = 50; int *b;
 b = &a;  // 배열일 때는, &안 붙여도 된다. 인덱싱 안 해주면 arr[0]부터 탐색 
 *b = *b + 20;
// out : a = 70, *b = 70
}
```

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/6f1df5e0-43d3-4e90-8bee-94e116c06680/Untitled.png)

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/5b9fa27b-e5d4-4719-b420-56e744ab8954/ae2c4f8c-50b1-4088-8f8b-1ac4382f01b0/Untitled.png)

### 06. 구조체

- 배열이 자료의 형과 크기가 동일한 변수의 모임이라면, 구조체는 자료의 종류가 다른 변수의 모임이다.
- 예약어는 `struct` (약간 자바의 class 느낌)

```c

// 구조체 선언
struct member {
	char name[10];
  int pay;
};

// 구조체 변수 선언
struct member member1, *mp;

// 구조체 멤버의 지정
	// 방법1 (.)
member1.name = "최수정";
member1.pay = 9000000;
	// 방법2 (->)
mp -> name = "최수정";
	// 방법3 (*포인터변수)
(*mp).name = "최수정";
(*member1).pay = 900000;
```

### 07.  파이썬

- 클래스

    ```python
    class 클래스명:
    	 실행할 문장
      def 메소드명 (self, 인수) : # 메소드에서 자기 클래스에 속한 변수에 접근할 때 사용하는 명칭 
    			return 
    ```