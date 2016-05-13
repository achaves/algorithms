package problems;

/**
 * Created by allenc289 on 4/22/16.
 */
public class Interval {
    int start;
    int end;

    public Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getMedian() {
        return (end - start) / 2 + start;
    }

    @Override
    public String toString() {
        return Integer.toString(start) + "," + Integer.toString(end);
    }

    public boolean isOpen() {
        return start <= end;
    }

    public void halveLeft() {
        if (end == getMedian()) {
            end--;
        } else {
            end = getMedian();
        }
    }

    public void halveRight() {
        if (start == getMedian()) {
            start++;
        } else {
            start = getMedian();
        }
    }

    public int size() {
        return end - start + 1;
    }
}
