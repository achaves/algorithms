package problems;

import algorithms.MaxPQ;

import java.util.PriorityQueue;

/**
 * Created by allenc289 on 4/3/16.
 */
public class MedianOfStream {
    PriorityQueue<Integer> minPQ = new PriorityQueue<Integer>();
    MaxPQ<Integer> maxPQ = new MaxPQ<Integer>();

    public void add(Integer element) {
        if (!maxPQ.isEmpty() && element <= maxPQ.max()) {
            maxPQ.insert(element);
        } else {
            if (!minPQ.isEmpty() && element >= minPQ.peek()) {
                minPQ.add(element);
            } else {
                minPQ.add(element);
            }
        }

        rebalance();
    }

    private void rebalance() {
        if (minPQ.size() > maxPQ.size() + 1) {
            Integer elementToMove = minPQ.poll();
            maxPQ.insert(elementToMove);
        }
        if (maxPQ.size() > minPQ.size() + 1) {
            Integer elementToMove = maxPQ.max();
            minPQ.add(elementToMove);
        }
    }

    public Double getMedian() {
        if (minPQ.size() == maxPQ.size()) {
            return (minPQ.peek() + maxPQ.max()) / 2.0;
        } else {
            if (minPQ.size() > maxPQ.size()) {
                return Double.valueOf(minPQ.peek());
            } else {
                return Double.valueOf(maxPQ.max());
            }
        }
    }

    public static void main(String[] args) {
        MedianOfStream mos = new MedianOfStream();

        mos.add(1);
        System.out.println(mos.getMedian());

        mos.add(2);
        System.out.println(mos.getMedian());

        mos.add(50);
        System.out.println(mos.getMedian());

        mos.add(4);
        System.out.println(mos.getMedian());

        mos.add(6);
        System.out.println(mos.getMedian());
    }
}
