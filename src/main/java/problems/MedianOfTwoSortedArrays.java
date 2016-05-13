package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by allenc289 on 4/3/16.
 */
public class MedianOfTwoSortedArrays {
    public static void testFindMedianSortedArrays() {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();

/*
        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{-50, -41, -40, -19, 5, 21, 28});
        Collections.addAll(b, new Integer[]{-50, -21, -10});
        System.out.println("-20.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{});
        Collections.addAll(b, new Integer[]{20});
        System.out.println("20.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{1, 2, 3, 4});
        Collections.addAll(b, new Integer[]{4, 5, 6});
        System.out.println("4.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{1, 2, 3, 4});
        Collections.addAll(b, new Integer[]{2, 2, 2});
        System.out.println("2.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Collections.addAll(b, new Integer[]{1, 1, 1});
        System.out.println("2.5= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{-43, -25, -18, -15, -10, 9, 39, 40});
        Collections.addAll(b, new Integer[]{-2});
        System.out.println("-10.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{-48, -43, 46});
        Collections.addAll(b, new Integer[]{18, 29});
        System.out.println("18.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{-48, -43, 46});
        Collections.addAll(b, new Integer[]{18});
        System.out.println("18.0= " + findMedianSortedArrays(a, b));

        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{0, 23});
        Collections.addAll(b, new Integer[]{});
        System.out.println("11.5= " + findMedianSortedArrays(a, b));
*/
        a.clear();
        b.clear();
        Collections.addAll(a, new Integer[]{-40, -25, 5, 10, 14, 28, 29, 48});
        Collections.addAll(b, new Integer[]{-48, -31, -15, -6, 1, 8});
        System.out.println("11.5= " + findMedianSortedArrays(a, b));
    }

    public static class Median {
        public double value;
        public int index;
        private int count;
        private int aboveCountFromOtherArray;
        private int belowCountFromOtherArray;

        public Median(double value, int index, int count) {
            this.value = value;
            this.index = index;
            this.count = count;
        }

        @Override
        public boolean equals(Object median) {
            return ((Median) median).value == this.value;
        }

        public int getAboveCountFromOtherArray() {
            return aboveCountFromOtherArray;
        }

        public void setAboveCountFromOtherArray(int aboveCountFromOtherArray) {
            this.aboveCountFromOtherArray = aboveCountFromOtherArray;
        }

        public int getBelowCountFromOtherArray() {
            return belowCountFromOtherArray;
        }

        public void setBelowCountFromOtherArray(int belowFromOtherArray) {
            this.belowCountFromOtherArray = belowFromOtherArray;
        }

        public int getBelowCount() {
            int below = index;
            return below + getBelowCountFromOtherArray();
        }

        public int getAboveCount() {
            int above = count - index - 1;
            return above + getAboveCountFromOtherArray();
        }

        public boolean isOverallMedian(int medianSize) {
            return getAboveCount() == getBelowCount() && getAboveCount() == medianSize;
        }
    }

    public static double findMedianSortedArrays(final List<Integer> a, final List<Integer> b) {
        if (a.isEmpty() && b.isEmpty()) {
            return -1;
        }
        if (a.isEmpty()) {
            return getMedian(b, new Interval(0, b.size() - 1));
        }
        if (b.isEmpty()) {
            return getMedian(a, new Interval(0, a.size() - 1));
        }

        int medianSize = getMedianSize(a, b);
        Interval intervalA = new Interval(0, a.size() - 1);
        Interval intervalB = new Interval(0, b.size() - 1);

        // We are going to loop until either the medians are the same
        // or we find that either A or B is the one
        while (intervalA.size() > 2 || intervalB.size() > 2) {
            Median medianA = getCandidateMedian(a, intervalA);
            Median medianB = getCandidateMedian(b, intervalB);

            if (medianA.value == medianB.value) {
                return medianA.value;
            }

            if (medianA.value < medianB.value) {
                medianB.setBelowCountFromOtherArray(medianA.getBelowCount() + 1);
                medianA.setAboveCountFromOtherArray(medianB.getAboveCount() + 1);

                medianB.setAboveCountFromOtherArray(0);
                medianA.setBelowCountFromOtherArray(0);

                // Only move the median that is farther from the real one
                if (Math.abs(medianA.getAboveCount() - medianA.getBelowCount()) > Math.abs(medianB.getAboveCount() - medianB.getBelowCount())
                        && intervalA.size() > 2) {
                    intervalA.halveRight();
                } else {
                    intervalB.halveLeft();
                }
            } else {
                medianA.setBelowCountFromOtherArray(medianB.getBelowCount() + 1);
                medianB.setAboveCountFromOtherArray(medianA.getAboveCount() + 1);

                medianA.setAboveCountFromOtherArray(0);
                medianB.setBelowCountFromOtherArray(0);

                // Only move the median that is farther from the real one
                if (Math.abs(medianA.getAboveCount() - medianA.getBelowCount()) > Math.abs(medianB.getAboveCount() - medianB.getBelowCount())
                        && intervalA.size() > 2) {
                    intervalA.halveLeft();
                } else {
                    intervalB.halveRight();
                }
            }

            if (medianA.isOverallMedian(medianSize)) {
                return medianA.value;
            }

            if (medianB.isOverallMedian(medianSize)) {
                return medianB.value;
            }

            if (medianA.getAboveCount() == medianB.getBelowCount() && medianA.getAboveCount() >= medianSize ||
                    medianA.getBelowCount() == medianB.getAboveCount() && medianA.getBelowCount() >= medianSize) {
                return (medianA.value + medianB.value) / 2.0;
            }
        }

        Median candidateMean1;
        if (a.get(intervalA.start) < b.get(intervalB.start)) {
            candidateMean1 = new Median(b.get(intervalB.start), intervalB.start, b.size());
            candidateMean1.setBelowCountFromOtherArray(intervalA.start + 1);
            candidateMean1.setAboveCountFromOtherArray(1);
        } else {
            candidateMean1 = new Median(a.get(intervalA.start), intervalA.start, a.size());
            candidateMean1.setBelowCountFromOtherArray(intervalB.start + 1);
            candidateMean1.setAboveCountFromOtherArray(1);
        }

        Median candidateMean2;
        if (a.get(intervalA.end) < b.get(intervalB.end)) {
            candidateMean2 = new Median(a.get(intervalA.end), intervalA.end, a.size());
            candidateMean2.setAboveCountFromOtherArray(b.size() - intervalB.start + 1);
            candidateMean2.setBelowCountFromOtherArray(1);
        } else {
            candidateMean2 = new Median(b.get(intervalB.end), intervalB.end, b.size());
            candidateMean2.setAboveCountFromOtherArray(a.size() - intervalA.start + 1);
            candidateMean2.setBelowCountFromOtherArray(1);
        }

        if (candidateMean1.isOverallMedian(medianSize)) {
            return candidateMean1.value;
        }

        if (candidateMean2.isOverallMedian(medianSize)) {
            return candidateMean2.value;
        }

        return -1;
    }

    private static Median getCandidateMedian(List<Integer> a, Interval interval) {
        double median = a.get(interval.getMedian());
        return new Median(median, interval.getMedian(), a.size());
    }

    private static double getMedian(List<Integer> a, Interval interval) {
        int medianSize = a.size();
        if (isEven(medianSize)) {
            medianSize = medianSize / 2 - 1;
        } else {
            medianSize = medianSize / 2;
        }

        return (a.get(medianSize) + a.get(a.size() - medianSize - 1)) / 2.0;
    }

    private static boolean isEven(int a) {
        return a % 2 == 0;
    }

    private static int getMedianSize(List<Integer> a, List<Integer> b) {
        int medianSize = a.size() + b.size();

        if (isEven(medianSize)) {
            medianSize = medianSize / 2 - 1;
        } else {
            medianSize = medianSize / 2;
        }

        return medianSize;
    }

}
