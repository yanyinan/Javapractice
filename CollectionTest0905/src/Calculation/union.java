package Calculation;

import java.util.HashSet;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */

/**
 * ## 并集计算
 *
 * 有两个List集合, 计算两个集合的并集元素。
 */
public class union {
    public static void main(String[] args) {
        HashSet set1 = new HashSet<>();
        HashSet set2 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");
        set2.add("b");
        set2.add("c");
        set2.add("d");
        HashSet result = new HashSet<>(set1);
//        result.addAll(set1);
        result.addAll(set2);
        System.out.println(result);
    }
}
