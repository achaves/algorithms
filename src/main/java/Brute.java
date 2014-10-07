import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by allenc289 on 10/5/14.
 */
public class Brute {
    public static void main(String[] args) {
        Point[] points = getPoints(args[0]);
        ArrayList<LineSegment> lineSegments = getLineSegments(points);

        drawPoints(points);
        drawLineSegments(lineSegments);
    }

    private static ArrayList<LineSegment> getLineSegments(Point[] points) {
        double slope;
        ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < points.length; k++) {
                    if (slope != points[i].slopeTo(points[k])) continue;

                    for (int l = k + 1; l < points.length; l++) {
                        if (slope == points[i].slopeTo(points[l])) {
                            Point[] lineSegmentPoints = new Point[4];
                            lineSegmentPoints[0] = points[i];
                            lineSegmentPoints[1] = points[j];
                            lineSegmentPoints[2] = points[k];
                            lineSegmentPoints[3] = points[l];

                            lineSegments.add(new LineSegment(lineSegmentPoints));
                        }
                    }
                }
            }
        }

        return lineSegments;
    }

    private static void drawLineSegments(ArrayList<LineSegment> lineSegments) {
        for (LineSegment lineSegment : lineSegments) {
            ((Point) lineSegment.getPoints()[0]).drawTo((Point) lineSegment.getPoints()[lineSegment.getPoints().length - 1]);

            StdOut.println(lineSegment.toString());
        }
        StdDraw.show();

    }

    private static void drawPoints(Point[] points) {
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.BLACK);

        for (Point point : points) {
            point.draw();
        }
        StdDraw.show(0);
    }

    private static Point[] getPoints(String arg) {
        In in = new In(arg);      // input file
        int N = in.readInt();
        Point[] points = new Point[N];
        int pointNr = 0;

        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();

            points[pointNr++] = new Point(x, y);
        }
        return points;
    }

    private static class LineSegment {
        private Point[] lineSegmentPoints;

        private LineSegment(Point[] points) {
            lineSegmentPoints = points;
            Arrays.sort(lineSegmentPoints);
        }

        private boolean contains(LineSegment that) {
            int thatPosition = 0;
            int thisPosition = 0;

            while (thisPosition < getPoints().length && thatPosition < that.getPoints().length) {
                if (getPoints()[thisPosition].compareTo(that.getPoints()[thatPosition]) == 0) {
                    thatPosition++;
                }
                thisPosition++;
            }
            return thatPosition == that.getPoints().length;
        }

        private Point[] getPoints() {
            return lineSegmentPoints;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < getPoints().length; i++) {
                sb.append(getPoints()[i].toString());
                if (i != getPoints().length - 1) {
                    sb.append(" -> ");
                }
            }

            return sb.toString();
        }
    }
}
