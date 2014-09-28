/**
 * Created by allenc289 on 9/27/14.
 */
public class Subset {
    public static void main(String[] args) {
        int k = new Integer(args[0]);

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }

        int i = 0;
        for (String s : randomizedQueue) {
            if (i == k) break;
            StdOut.println(s);
            i++;
        }
    }
}
