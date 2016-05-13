package problems;

import java.util.ArrayList;

/**
 * Created by allenc289 on 4/22/16.
 */
public class PrettyPrint {
    public static void testPrettyPrint() {
        System.out.println(prettyPrint(3));
    }

    public static ArrayList<ArrayList<Integer>> prettyPrint(int a) {
        int sideSize = a * 2 - 1;
        int center = a;
        ArrayList<ArrayList<Integer>> result = new ArrayList();

        for (int i = 0; i < sideSize; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            result.add(row);
            for (int j = 0; j < sideSize; j++) {
                int x = Math.abs(i - sideSize / 2) + 1;
                int y = Math.abs(j - sideSize / 2) + 1;
                int minDistance = Math.max(x, y);
                Integer value = minDistance;
                row.add(value);
            }
        }

        return result;
    }
}
