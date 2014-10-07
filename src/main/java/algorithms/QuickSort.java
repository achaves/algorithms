package algorithms;

/**
 * Created by allenc289 on 10/2/14.
 */
public class QuickSort {


    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while (true) {
            while (a[++i].compareTo(a[lo]) < 0) {
                if (i == hi) break;
            }

            while (a[lo].compareTo(a[--j]) < 0) {
                if (j == lo) break;
            }

            if (i >= j) break;
            exch(a, i, j);
        }

        exch(a, lo, j);
        printArray(a);
        return j;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }


    private static void printArray(Comparable[] a) {
        for (Comparable el : a) {
            System.out.print(el + " ");
        }
        System.out.println();
    }


    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, gt =hi;
        Comparable v = a[lo];
        int i = lo;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) exch(a, lt++, i++);
            else if (cmp > 0) exch(a, i, gt--);
            else i++;
        }
        printArray(a);

        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    public static void main(String[] args) {
       Comparable[] a = new Comparable[12];
        int i = 0;
        for (String number : "76 15 96 24 87 72 92 40 82 65 22 84".split(" ")) {
            a[i++] = number; //Integer.valueOf(number);
        }
        System.out.println(i);

        partition(a, 0, a.length - 1);
        //sort(a, 0, a.length - 1);

    }

}
