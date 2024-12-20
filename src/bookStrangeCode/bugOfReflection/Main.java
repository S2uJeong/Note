package bookStrangeCode.bugOfReflection;

import java.lang.reflect.Field;

/**
 * Level 클래스는 설계 규칙을 잘 만들어 작성했지만,
 * 실행할 때 리플렉션을 사용해서 규칙이 다 깨져버렸다.
 *
 * 객체 필드 검증이 적용 안되고 있음
 */
public class Main {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Level level = Level.initialize();
        System.out.println(level.value);

        Field field = Level.class.getDeclaredField("value");
        field.setAccessible(true);
        field.setInt(level, 999); // MAX가 99인데,,
        System.out.println(level.value);
    }
}
