package bookStrangeCode.goodComment;

import java.util.HashSet;
import java.util.Set;

class States {
    private final Set<State> stateSet = new HashSet<>();

    /**
     * 상태 리스트에 상태를 추가한다.
     * @param state 캐릭터 상태
     * @throws IllegalArgumentException 상태에 null이 들어오면 발생
     */
    public void addState(State state) {
        if (state == null) throw new IllegalArgumentException("상태에 올바르지 않은 값이 들어왔습니다.");
        stateSet.add(state);
    }
    public void removeState(State state) {
        stateSet.remove(state);
    }
    boolean contains(StateType stateType) {
        for (State state : stateSet) {
            if (state.type() == stateType) {
                return true;
            }
        }
        return false;
    }
}
