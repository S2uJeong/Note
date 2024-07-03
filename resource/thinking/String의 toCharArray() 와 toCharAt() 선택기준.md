아래 코드는 매개변수로 받는 String에 대해 숫자를 포함하고 있는지 확인하는 기능을 만든 것이다.

```java
public class PasswordStrengthMeter {
    public PasswordStrength meter(String s) {
        
    boolean isContainsNum = false;
    // 1. toCharArray() 사용 
    for(char ch :s.toCharArray()) {
        if (ch >= '0' && ch <= '9') {
            isContainsNum = true;
            break;
         }
      }
    // 2. toCharAt() 사용
    for (int i = 0; i < s.length(); i++) {
        char ch = s.charAt(i);
        if (ch >= '0' && ch <= '9') {
            isContainsNum = true;
            break;
        }
      }
    }
}
```
이렇게 `toCharArray()`, `toCharAt()` 두 개의 메서드 모두 기능을 만드는데 사용할 수 있는데 어떤 게 좋은건지 가독성, 성능 부문에서 생각해 보았다. 

우선 두 메서드 모두 String 클래스에 포함된 메서드 이며 
- `toCharArray()`은 String에 대해 한 글자씩 Char형으로 바꾸어 list로 만드는 메서드이다. 
- 탐색에 드는 시간 O(N), 배열을 만드는데 사용되는 공간 O(N)이 사용된다.

- `toCharAt()` 은 저장 공간을 따로 만들지 않고, 매개변수로 주어지는 int형 자료에 대해 인덱스로 해당 글자만을 탐색한다.
- 탐색에 드는 시간 O(N) 만이 소요된다.

따라서 메모리, 속도를 생각한 성능 차원에선 `toCharAt()`을 쓰는 것이 맞다. 
하지만 코드의 가독성 측면에서 String을 문자 배열화를 함으르써 향상된 for 루프를 사용할 수 있어 사용을 고민할 수 있다. 