package problems;

/**
 * Created by allenc289 on 4/22/16.
 */
public class ExcelNumberToTitle {
    public static void testConvertToTile() {
        /*System.out.println("A: " + convertToTitle(1));
        System.out.println("AA: " + convertToTitle(27));
        System.out.println("BA: " + convertToTitle(53));
        System.out.println("AAA: " + convertToTitle(703));*/

        System.out.println("BAQTZ: " + convertToTitle(943566) + " " + Integer.toString(943566, 26));
    }

    public static String convertToTitle(int a) {
        String result = new String();
        while (a > 0) {
            int r = (a - 1) % 26;

            if (r == 0) {
                result = 'Z' + result;
                a = a / 26 - 1;
            } else {
                result = ((char) ('A' + r - 1)) + result;
                a = a / 26 - 1;
            }

        }
        return result.toString();
    }
}
