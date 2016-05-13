package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by allenc289 on 4/22/16.
 */
public class SearchRange {


    public static int binarySearch(final List<Integer> a, int start, int end, int b) {
        /*
                int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
         */
        while (end >= start) {
            int mid = (end - start) / 2 + start;
            int midElement = a.get(mid);
            if (midElement == b) {
                return mid;
            } else {
                if (midElement < b) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public static ArrayList<Integer> searchRange(final List<Integer> a, int b) {
        ArrayList<Integer> result = new ArrayList<>();
        int minLocationOfB = -1;
        int maxLocationOfB = -1;

        int indexOfB = binarySearch(a, 0, a.size() - 1, b);

        // We found one b at position indexOfB (or we have -1 to indicate we didn't)
        // Now we need to look to see how many repeated elements are there

        // between indexOfB + 1 .. end. Note that if indexOfB = -1 this is a no-op
        int candidateMaxLocationOfB = indexOfB;
        while (candidateMaxLocationOfB != -1) {
            maxLocationOfB = candidateMaxLocationOfB;
            candidateMaxLocationOfB = binarySearch(a, maxLocationOfB + 1, a.size() - 1, b);
        }

        // and also between start and indexOfB - 1. Note that if indexOfB = -1 this is a no-op
        int candidateMinLocationOfB = indexOfB;
        while (candidateMinLocationOfB != -1) {
            minLocationOfB = candidateMinLocationOfB;
            candidateMinLocationOfB = binarySearch(a, 0, minLocationOfB - 1, b);
        }

        // return found values
        result.add(minLocationOfB);
        result.add(maxLocationOfB);
        return result;
    }

    private static void testSearchRange() {
        Integer[] b = new Integer[]{5, 7, 7, 8, 8, 10};

        ArrayList<Integer> a = new ArrayList<>();
        Collections.addAll(a, b);

        System.out.println(searchRange(a, 8));
    }
}
