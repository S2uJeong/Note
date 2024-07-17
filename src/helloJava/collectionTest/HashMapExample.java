package helloJava.collectionTest;
import java.util.*;
import java.util.Map.Entry;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();

        map.put("최원규",60);
        map.put("최수정", 26);
        map.put("최인정", 29);
        map.put("최원규",61); // key가 중복되어, 마지막 값만 저장 된다.

        System.out.println("총 Entry 수 : " + map.size());

        // key Set 컬렉션을 얻고, 반복해서 출력
        Set<String> keySets = map.keySet();
        System.out.print("최씨 가족 들 : ");
        Iterator<String> it = keySets.iterator();
        while(it.hasNext()) {
            System.out.print(it.next() + ", ");
        }
        System.out.println();

        // 키로 값 얻기
        System.out.println("key : " + "최수정 , value: " + map.get("최수정"));
        System.out.println("============================================");

        // 엔트리 Set 컬렉션을 얻고, 반복해서 키와 값 얻기
        Set<Entry<String, Integer>> entrysSets = map.entrySet();
        for (Entry<String,Integer> entry : entrysSets) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            System.out.println(k + " : " + v);
        }
        System.out.println();
    }
}
