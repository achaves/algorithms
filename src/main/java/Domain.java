/*************************************************************************
 *  Compilation:  javac Domain.java
 *  Execution:    java Domain < input.txt
 *
 *  Sort by reverse domain name.
 *
 *  % java Domain < domains.txt
 *  com.apple
 *  com.cnn
 *  com.google
 *  edu.princeton
 *  edu.princeton.cs
 *  edu.princeton.cs.bolle
 *  edu.princeton.cs.www
 *  edu.princeton.ee
 *
 *************************************************************************/

import java.util.Arrays;

public class Domain implements Comparable<Domain> {
    private final String[] fields;
    private final int N;

    // store fields in reverse order
    public Domain(String name) {
        fields = name.split("\\.");
        N = fields.length;
    }

    // return string representation - fields, delimited by .
    public String toString() {
        if (N == 0) return "";
        String s = fields[0];
        for (int i = 1; i < N; i++)
            s = fields[i] + "." + s;
        return s;
    }

    // compare by reverse domain name
    public int compareTo(Domain that) {
        for (int i = 0; i < Math.min(this.N, that.N); i++) {
            String s = this.fields[this.N - i - 1];
            String t = that.fields[that.N - i - 1];
            int c = s.compareTo(t);
            if      (c < 0) return -1;
            else if (c > 0) return +1;
        }
        return this.N - that.N;
    }





    // test client
    public static void main(String[] args) {

        // read in domain names
        String[] names = StdIn.readAllStrings();
        Domain[] domains = new Domain[names.length];
        for (int i = 0; i < domains.length; i++) {
            domains[i] = new Domain(names[i]);
        }

        // sort
        Arrays.sort(domains);

        // print results
        for (int i = 0; i < domains.length; i++) {
            StdOut.println(domains[i]);
        }
    }

}