package problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by allenc289 on 4/13/16.
 */
public class KnightWalk {

    public List<Point> getKnightMoves(Point from, int N, int M) {
        ArrayList<Point> result = new ArrayList<>();

        for (int i = -2; i <=2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 3) {
                    if (from.x + i >= 0 && from.x + i < N && from.y + j >= 0 && from.y + j < M) {
                        result.add(new Point(from.x + i, from.y + j));
                    }
                }
            }
        }

        return result;
    }

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            Point otherPoint = (Point) o;
            return otherPoint.x == this.x && otherPoint.y == this.y;
        }
    }

    public int knightWalk(int N, int M, Point from, Point to) {
        if (from.equals(to)) return 0;

        Queue<Point> queue = new LinkedList<Point>();
        queue.add(from);

        int[][] distanceTo = new int[N][M];
        boolean[][] hasBeenVisited = new boolean[N][M];
        while (!queue.isEmpty()) {
            Point p = queue.poll();

            for (Point adjacent: getKnightMoves(p, N, M)) {
                if (!hasBeenVisited[adjacent.x][adjacent.y]) {
                    distanceTo[adjacent.x][adjacent.y] = distanceTo[p.x][p.y] + 1;

                    if (adjacent.equals(to)) {
                        return distanceTo[to.x][to.y];
                    }

                    queue.add(adjacent);
                    hasBeenVisited[adjacent.x][adjacent.y] = true;
                }
            }
        }

        return -1;

    }



    public static void main (String[] args) {
        KnightWalk kw = new KnightWalk();

        System.out.println(kw.knightWalk(8, 8, new Point(0, 0), new Point(2, 4)));
    }
}
