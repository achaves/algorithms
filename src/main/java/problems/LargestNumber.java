package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by allenc289 on 4/22/16.
 */
public class LargestNumber {
    // [3, 30, 34, 5, 9], the largest formed number is 9 5 34 3 30
    // 1, 50 -> 501
    // 99197494093090589587787286882579979879178278077273570968668667867566465335024704093953663633573372929827126124019124113
    // 99197494093090589587787286882579979879178278077273570968668667867566465335024704093953663633573372982927126124019124113
    public static void testLargestNumber() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(3);
        a.add(30);
        a.add(34);
        a.add(5);
        a.add(9);

        System.out.println(largestNumber(a));

        a.clear();
        a.add(1);
        a.add(50);

        System.out.println(largestNumber(a));

        a.clear();
        a.add(0);
        a.add(0);

        System.out.println(largestNumber(a));

    }

    public static class IntegerComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer o1, Integer o2) {
            String o1Str = o1.toString();
            String o2Str = o2.toString();

            int biggestLength = Math.max(o1Str.length(), o2Str.length());

            int o1Pos = 0;
            int o2Pos = 0;

            for (int i = 0; i < biggestLength; i++) {
                int comp = o2Str.charAt(o2Pos) - o1Str.charAt(o1Pos);

                if (comp != 0) {
                    return comp;
                }

                if (o1Pos != o1Str.length() - 1) {
                    o1Pos++;
                }
                if (o2Pos != o2Str.length() - 1) {
                    o2Pos++;
                }
            }

            return o2Pos - o1Pos;
        }
    }

    public static String largestNumber(final List<Integer> a) {
        ArrayList<Integer> copy = new ArrayList<>(a);
        Collections.sort(copy, new IntegerComparator());

        StringBuilder response = new StringBuilder();
        for (Integer i : copy) {
            if (i != 0) {
                response.append(i.toString());
            }
        }

        if (response.length() == 0) {
            return "0";
        } else {
            return response.toString();
        }
    }
}
