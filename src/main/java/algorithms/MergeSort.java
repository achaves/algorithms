package algorithms;

import java.io.Console;

/**
 * Created by allenc289 on 10/2/14.
 */
public class MergeSort {


    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static void sortBU(Comparable[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];
        for (int sz = 1; sz < N; sz = sz+sz) {
            for (int lo = 0; lo < N - sz; lo += sz+sz) {
                merge(a, aux, lo, lo + sz - 1, Math.min(lo+sz+sz-1, N-1));
            }
        }

    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                           a[k] = aux[j++];
            else if (j > hi)                       a[k] = aux[i++];
            else if (aux[j].compareTo(aux[i]) < 0) a[k] = aux[j++];
            else                                   a[k] = aux[i++];
        }

        for (Comparable el : a) {
            System.out.print(el + " ");
        }
        System.out.println();

    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);

    }

    public static void main(String[] args) {
       Comparable[] a = new Comparable[15];
        int i = 0;
        for (String number : "36 94 76 64 71 21 74 53 38 83 44 44 55 54 45".split(" ")) {
            a[i++] = Integer.valueOf(number);
        }
        System.out.println(i);
        /*
        a[0] = 92;
        a[1] = 47;
        a[2] = 45;
        a[3] = 83;
        a[4] = 60;
        a[5] = 21;
        a[6] = 10;
        a[7] = 78;
        a[8] = 58;
        a[9] = 65;
        a[10] = 55;
        a[11] = 27;

        sort(a);
        */
        sort(a);

    }

}
