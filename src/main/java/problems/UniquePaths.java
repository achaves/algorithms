package problems;

/**
 * Created by allenc289 on 4/22/16.
 */
public class UniquePaths {
    public static void testUniquePaths() {
        System.out.println("5X5= " + uniquePaths(5, 5));
        System.out.println("5X6= " + uniquePaths(5, 6));
    }

    public static int uniquePaths(int a, int b) {
        int[] rowValues = new int[b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (i == 0 || j == 0) {
                    rowValues[j] = 1;
                } else {
                    rowValues[j] = rowValues[j] + rowValues[j - 1];
                }
            }
        }

        return rowValues[b - 1];
    }
}
