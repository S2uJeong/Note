jsp파일을 수정하면서 많은 문법들이 섞여 있었는데 정확히 저 단어들이 의미하는 것이 무엇이고 어떤 방식으로 주로 활용하는지 알아본다.

- 실행 순서, 주요 기능, 대표 문법을 기준으로 비교한다. 


## 실행 순서 비교
| 기술                            | 실행 시점                             | 실행 환경                  | 역할                                     |
| ------------------------------- | ------------------------------------- | -------------------------- | ---------------------------------------- |
| EL (Expression)                 | JSP가 서버에서 실행될 때 (컴파일 전)  | 서버 (JSP 내부)            | JSP에서 간단한 값을 출력하거나 변수 연산 |
| JSTL (JSP standard Tag Library) | JSP가 서버에서 실행될 때 (컴파일 전)  | 서버 (JSP 내부)            | JSP에서 반복문, 조건문 등을 쉽게 사용    |
| jQuery                          | HTML이 브라우저에서 렌더링 된 후 실행 | 클라이언트 (브라우저 내부) | CSS/HTML/AJAX , 이벤트 처리              |

## 활용
- EL : request, session, application 속성 값 출력
- JSTL : if, forEach, choose 등을 사용해 동적 페이지 구현
- HTML 요소를 변경하거나, AJAX 요청, 애니메이션 구현

## 각 기능 대표 문법
### EL
- `${}` 문법을 사용하여 request, session, application 영역의 속성을 출력
```jsp
<!-- 값 출력 예시 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    request.setAttribute("name", "Sujeong");
%>
이름: ${name}  <!-- 출력 결과: Sujeong -->


<!-- 연산 예시 -->
```jsp
${10 + 20}
${param.age > 18 ? '성인' : '미성년자'}  // URL 파라미터 age에 따라 출력
```

### JSTL
```jsp
    <c:forEach var="i" begin="1" end="5">
    숫자: ${i} <br>
    </c:forEach>

    <c:if test="${user.age >= 18}">
    성인입니다.
    </c:if>
```

### jQuery
```jsp
// 요소 클릭 이벤트
<button id="btn">클릭하세요</button>
<script>
    $("#btn").click(function() {
        alert("버튼이 클릭되었습니다.");
    });
</script>

// jQuery 안 쓰면?
<script>
let bt = document.getElementId("btn");
bt.addEventListener("click", function() {
       alert("버튼이 클릭되었습니다.");
});
</script>

```

## 실행 순서
1. JSP 실행 (서버 실행)
    - EL & JSTL이 서버에서 실행 -> HTML 코드 생성
    - 이때, EL을 통해 값이 출력되고, JSTL을 통해 반복문과 조건문이 적용됨
2. HTML이 브라우저로 전송됨
    - 서버에서 실행된 결과(HTML 코드)가 클라인 브라우저로 전달
3. 클라이언트에서 jQuery 실행
    - 브라우저에서 HTML 렌더링한 후 jQuery가 실행됨
    - 사용자의 클릭, 입력 등의 이벤트를 감지하고 DOM을 조작한다.
    - AJAX 요청을 총해 데이터를 서버와 주고받을 수 있다.

### 예제 코드
```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    request.setAttribute("name", "Sujeong");
    request.setAttribute("numbers", new int[]{1, 2, 3, 4, 5});
%>

<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

    <h1>이름: ${name}</h1>  <!-- EL 사용 -->

    <ul>
        <c:forEach var="num" items="${numbers}">  <!-- JSTL 반복문 -->
            <li>${num}</li>
        </c:forEach>
    </ul>

    <button id="btn">클릭하세요</button>

    <script>
        $("#btn").click(function() {  // jQuery 사용
            alert("버튼이 클릭되었습니다!");
        });
    </script>

</body>
</html>
```

### 각 기능들에서 변수 처리
- JSTL 쓸 때 ${i}와 "${}" 의 차이는 뭐지?
- "{$__}" : JSTL에서 데이터 가져오는 부분
- ${} : EL에서 데이터 가져오는 부분
- ${"__"} : 선택자, jQuery