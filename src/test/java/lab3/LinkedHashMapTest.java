package lab3;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LinkedHashMapTest {
    @Test
    void test() {
        final var stringVoidLinkedHashMap = new LinkedHashMap<String, Integer>();
        stringVoidLinkedHashMap.put("1", 0);
        stringVoidLinkedHashMap.put("2", 1);
        stringVoidLinkedHashMap.put("3", 2);
        stringVoidLinkedHashMap.put("4", 3);
        stringVoidLinkedHashMap.put("5", 4);
        stringVoidLinkedHashMap.put("6", 5);
        System.out.println(stringVoidLinkedHashMap.get("2"));
        System.out.println(stringVoidLinkedHashMap);
        loopLinkedHashMap(stringVoidLinkedHashMap);
    }
    public static <K, V>void loopLinkedHashMap(LinkedHashMap<K, V> linkedHashMap)
    {
        Set<Map.Entry<K, V>> set = linkedHashMap.entrySet();
        Iterator<Map.Entry<K, V>> iterator = set.iterator();

        while (iterator.hasNext())
        {
            System.out.print(iterator.next() + "\t");
        }
        System.out.println();
    }
}
