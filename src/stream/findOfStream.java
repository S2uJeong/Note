package stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * filter()로 스트림 요소에서 값을 추리고 특정 값을 찾아 Optional로 반환하는 findAny()
 *                                                              - 제일 먼저 나오는 하나만 저장
 * Optional - isPresent()
 * List - .collect(Collectors.toList())
 */
public class findOfStream {

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        Optional<Integer> targetedNum = nums.stream()
                                            .filter( n ->  n % 2 == 0)
                                            .findAny();

        if (targetedNum.isPresent()) {
            System.out.println(targetedNum);
            System.out.println("찾은 요소 : " + targetedNum.get());
        } else {
            System.out.println("요소를 찾지 못했습니다.");
        }

        System.out.println("=========== filter() + collect() 로 싹 다 찾기 ========");
        List<Integer> targetedNum2 = nums.stream()
                                .filter( n ->  n % 2 == 0)
                                .collect(Collectors.toList());

        if (targetedNum2.isEmpty()) {
            System.out.println("요소를 찾지 못했습니다.");
        } else {
            System.out.println(targetedNum2);
        }
    }





}
