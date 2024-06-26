## Deque; Double Ended Queue

- 양쪽 끝에서 요소를 추가하고 제거할 수 있는 자료구조 입니다.
-  Java에서 Duque를 구현한 클래스 중 자주 사용되는 것은 ArrayDeque, LinkedList 입니다.

### Deque 인터페이스

- 주요메서드

  ```java
  void addFirst(E e); // 저장된 요소를 덱의 앞에 삽입
  void addLast(E e);
  E removeFirst(); // 덱의 앞 요소를 제거하고 반환
  E removeLast();
  E getFirst(); // 덱의 앞 요소 반환 
  E pollLast();
  ```

### LinkedList

- 이중 연결 리스트로 구현된 자료구조이다.
- 따라서 각 요소마다 추가적인 객체(노드)를 저장해야 하므로 메모리 사용량이 더 많을 수 있다.

| 메서드                    | 시간복잡도 |
| ------------------------- | ---------- |
| 삽입/제거                 | O(1)       |
| 임의의 위치에서 삽입/제거 | O(n)       |
| 탐색(index)               | O(N)       |

### ArrayDeque

- 배열 기반 자료구조라 메모리 할당이 더 효율적이다.

| 메서드                    | 시간복잡도     |
| ------------------------- | -------------- |
| 삽입/제거                 | O(1) 평균      |
| 임의의 위치에서 삽입/제거 | 해당 연산 없음 |
| 탐색(index 불가, 앞뒤만)  | O(1)           |

- 삽입 시 배열의 크기가 부족하면 재할당이 일어나기 때문에 평균적으로 O(1)의 시간 복잡도를 가진다.

### 선택 기준 : 그럼 어떤걸 써야 할까?

|                  | LinkedList  | ArrayDeque  |
| ---------------- | ----------- | ----------- |
| 삽입/제거        | 비교적 빠름 | 비교적 느림 |
| 인덱스 기반 접근 | 비교적 느림 | 비교적 빠름 |

- LinkedList : 양 끝뿐만 아니라 중간에서도 빈번한 삽입, 삭제, 수정 연산이 필요한 경우 적합하다.

- ArrayDeque : 양 끝에서 삽입 및 삭제가 빈번하며, 임의의 위치에서 접근이 필요하지 않는 경우 적합하다. 

  