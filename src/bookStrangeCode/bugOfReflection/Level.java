package bookStrangeCode.bugOfReflection;

public class Level {
    private static final int MIN = 1;
    private static final int MAX = 99;
    final int value;

    public Level(int value) {
        if (value < MIN || MAX < value) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    // 초기 레벨 리턴
    static Level initialize() {
        return new Level(MIN);
    }
    // 레벨업
    Level increase() {
        if (value < MAX) return new Level(value+1);
        return this;
    }
}
