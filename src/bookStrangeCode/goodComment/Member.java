package bookStrangeCode.goodComment;

class Member {
    private final States states;

    Member(States states) {
        this.states = states;
    }

    // 고통받는 상태일 때 true를 리턴
    boolean isPainful() {
        // 이후 사양 변경으로 표정 변화를
        // 일으키는 상태를 추가할 경우
        // 이 메서드에 로직을 추가한다.
        if (states.contains(StateType.POISON) ||
                states.contains(StateType.PARALYZED) ||
                states.contains(StateType.FEAR)) {
            return true;
        }
        return false;
    }
}
