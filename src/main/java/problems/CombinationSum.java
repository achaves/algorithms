package problems;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by allenc289 on 4/22/16.
 */
public class CombinationSum {
    public static void testCombinationSum() {
        ArrayList<Integer> a = new ArrayList<>();
        Collections.addAll(a, new Integer[]{10, 1, 2, 7, 6, 1, 5});

        System.out.println(combinationSum(a, 8));

    }

    public static ArrayList<ArrayList<Integer>> combinationSum(ArrayList<Integer> a, int sum) {
        ArrayList<ArrayList<Integer>> combinations = new ArrayList<>();
        ArrayList<Integer> combination = new ArrayList<>();
        combine(a, 0, sum, combination, combinations);

        return combinations;
    }

    private static void combine(ArrayList<Integer> a, int pos, int sum, ArrayList<Integer> solution, ArrayList<ArrayList<Integer>> solutions) {
        if (sum == 0) {
            ArrayList<Integer> list = new ArrayList<>();
            list.addAll(solution);
            solutions.add(list);
            return;
        }

        for (int i = pos; i < a.size(); i++) {
            if (a.get(i) <= sum) {
                solution.add(a.get(i));
                combine(a, i + 1, sum - a.get(i), solution, solutions);
                solution.remove(solution.size() - 1);
            }
        }

    }
}
