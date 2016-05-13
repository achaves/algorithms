package problems;

/**
 * Created by allenc289 on 4/22/16.
 */
public class ExcelTitleToNumber {
    public static void testTitleToNumber() {
        System.out.println("A: " + titleToNumber("A"));
        System.out.println("AA: " + titleToNumber("AA"));
        System.out.println("BA: " + titleToNumber("BA"));
        System.out.println("AAA: " + titleToNumber("AAA"));
    }

    public static int titleToNumber(String a) {
        int number = 0;
        for (int i = 0; i < a.length(); i++) {
            int digitValue = (a.charAt(i) - 'A' + 1);
            int power = a.length() - i - 1;
            number += Math.pow(26, power) * digitValue;
        }
        return number;
    }
}
