package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

public class Test {























    static List<List<String>> ret;

    public static List<List<String>> partition(String s) {
        int n = s.length();
        boolean[][] isPalindrome = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 2) {
                        int x = 99;
                    }
                    if (j - i < 2 || isPalindrome[i + 1][j - 1]) {
                        isPalindrome[i][j] = true;
                    }
                }
            }
        }
        ret = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isPalindrome[i][j]) {

                }
            }
        }
        List<String> list = new LinkedList<>();
        partition(s, 0, isPalindrome, list);
        return ret;
    }

    private static void partition(String s, int start, boolean[][] isPalindrome, List<String> list) {
        if (start == s.length()) {
            List<String> newList = new LinkedList<>();
            newList.addAll(list);
            ret.add(newList);
            return;
        }
        for (int i = start; i < s.length(); i++) {
            if (isPalindrome[start][i]) {
                list.add(s.substring(start, i + 1));
                partition(s, i + 1, isPalindrome, list);
                list.remove(list.size() - 1);
            }
        }
    }



    public static void testdNums() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Collections.addAll(numbers, new Integer[]{1, 2, 1, 3, 4, 3});

        System.out.println(dNums(numbers, 3));
    }

    private static void updateNumberCount(Hashtable<Integer, Integer> numberCounts, int number, int increment) {
        int count = 0;
        if (numberCounts.containsKey(number)) {
            count = numberCounts.get(number);
        }
        count += increment;
        if (count > 0) {
            numberCounts.put(number, count);
        } else {
            numberCounts.remove(number);
        }
    }

    public static ArrayList<Integer> dNums(ArrayList<Integer> numbers, int windowSize) {
        ArrayList<Integer> result = new ArrayList<>();

        if (windowSize <= numbers.size()) {
            int numOfWindows = numbers.size() - windowSize + 1;
            Hashtable<Integer, Integer> numberCounts = new Hashtable<>();

            // Add first windowSize - 1 to numberCounts
            for (int i = 0; i < windowSize - 1; i++) {
                updateNumberCount(numberCounts, numbers.get(i), 1);
            }


            for (int windowNumber = 0; windowNumber < numOfWindows; windowNumber++) {
                updateNumberCount(numberCounts, numbers.get(windowNumber + windowSize - 1), 1);
                result.add(numberCounts.size());
                updateNumberCount(numberCounts, numbers.get(windowNumber), -1);
            }
        }

        return result;

    }

    public static void main(String[] args) {
        //testMaxSet();

        //testInterval();

        //testLargestNumber();

        // testTitleToNumber();

        // testConvertToTile();

        //testUniquePaths();

        //testPrettyPrint();

        //testSearchRange();

        //testFindMedianSortedArrays();

        //testReverseBetween();

        //System.out.println(partition("aab"));

        //testCombinationSum();

        testdNums();
    }


}
