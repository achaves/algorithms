package problems;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by allenc289 on 4/22/16.
 */
public class MaxSet {

    private static void testMaxSet() {
        ArrayList<Integer> a = new ArrayList();
        a.add(1);
        a.add(2);
        a.add(5);
        a.add(-7);
        a.add(2);
        a.add(5);
        a.clear();

        a.add(0);
        a.add(0);
        a.add(-1);
        a.add(0);

        System.out.println(new MaxSet().maxset(a).toString());
    }

    public ArrayList<Integer> maxset(ArrayList<Integer> a) {
        ArrayList<Integer> bestSequence = new ArrayList();
        Double bestSum = 0.0;

        Double currentSum = 0.0;
        ArrayList<Integer> currentSequence = new ArrayList();
        Boolean inSequence = false;
        for (int i = 0; i < a.size(); i++) {
            inSequence = a.get(i) >= 0;

            if (inSequence) {
                currentSequence.add(a.get(i));
                currentSum += a.get(i);
            }

            if (!inSequence || i == a.size() - 1) {
                if (currentSum > bestSum ||
                        Objects.equals(currentSum, bestSum) &&
                                currentSequence.size() > bestSequence.size()) {
                    bestSum = currentSum;
                    bestSequence.clear();
                    bestSequence.addAll(currentSequence);
                }

                // clear current values
                currentSum = 0.0;
                currentSequence.clear();
            }
        }

        return bestSequence;
    }
}
