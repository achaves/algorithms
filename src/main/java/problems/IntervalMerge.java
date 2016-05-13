package problems;


import java.util.ArrayList;

/**
 * Created by allenc289 on 4/22/16.
 */
public class IntervalMerge {
    private static void testInterval() {
        ArrayList<Interval> intervals = new ArrayList<>();
        intervals.add(new Interval(1, 3));
        intervals.add(new Interval(6, 9));

        System.out.println(insert(intervals, new Interval(2, 5)));

        intervals.clear();
        intervals.add(new Interval(1, 2));
        intervals.add(new Interval(3, 5));
        intervals.add(new Interval(6, 7));
        intervals.add(new Interval(8, 10));
        intervals.add(new Interval(12, 16));

        System.out.println(insert(intervals, new Interval(4, 9)));
    }

    private static void addIntervalWithMerge(ArrayList<Interval> intervals, Interval intervalToMerge) {
        if (intervals.size() == 0) {
            intervals.add(intervalToMerge);
            return;
        }

        Interval lastIntervalInResult = intervals.get(intervals.size() - 1);

        // We know that this.start > lastIntervalInResult.start
        if (intervalToMerge.end <= lastIntervalInResult.end) {
            // nothing to do
        } else {
            if (intervalToMerge.start > lastIntervalInResult.end) {
                intervals.add(intervalToMerge);
            } else {
                lastIntervalInResult.end = intervalToMerge.end;
            }
        }
    }

    public static ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        ArrayList<Interval> result = new ArrayList();

        int positionToAdd = 0;
        while (positionToAdd < intervals.size() && newInterval.start > intervals.get(positionToAdd).start) {
            result.add(intervals.get(positionToAdd));
            positionToAdd++;
        }

        addIntervalWithMerge(result, newInterval);

        for (int i = positionToAdd; i < intervals.size(); i++) {
            Interval currentInterval = intervals.get(i);
            addIntervalWithMerge(result, currentInterval);
        }

        return result;
    }
}
