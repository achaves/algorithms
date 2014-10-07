import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * Created by allenc289 on 10/5/14.
 */
public class Fast {
    private static final int MINIMUM_SEGMENT_SIZE = 4;

    public static void main(String[] args) {
        Point[] points = getPoints(args[0]);
        Hashtable<String, LineSegment> lineSegments = getLineSegments(points);

        drawPoints(points);
        drawLineSegments(lineSegments);
    }

    private static Hashtable<String, LineSegment> getLineSegments(Point[] points) {
        //Think of p as the origin.
        //For each other point q, determine the slope it makes with p.
        //Sort the points according to the slopes they make with p.
        //Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p.
        // If so, these points, together with p, are collinear
        Hashtable<String, LineSegment> lineSegments = new Hashtable<String, LineSegment>();
        for (int i = 0; i < points.length - 1; i++) {
            ArrayList<Point> lineSegmentPoints = new ArrayList<Point>();

            Arrays.sort(points, i + 1, points.length, points[i].SLOPE_ORDER);

            int currentPosition = i + 1;
            double currentSlope = Double.NaN;
            while (currentPosition < points.length) {
                double thisSlope = points[i].slopeTo(points[currentPosition]);
                if (thisSlope == currentSlope) {
                    lineSegmentPoints.add(points[currentPosition]);
                } else {
                    addToLineSegmentArray(lineSegments, lineSegmentPoints, currentSlope);

                    currentSlope = thisSlope;
                    lineSegmentPoints.clear();
                    lineSegmentPoints.add(points[i]);
                    lineSegmentPoints.add(points[currentPosition]);
                }

                currentPosition++;
            }
            addToLineSegmentArray(lineSegments, lineSegmentPoints, currentSlope);
        }
        return lineSegments;
    }

    private static void addToLineSegmentArray(Hashtable<String, LineSegment> lineSegments, ArrayList<Point> lineSegmentPoints, double slope) {
        if (lineSegmentPoints.size() >= MINIMUM_SEGMENT_SIZE) {
            LineSegment lineSegmentToAdd = new LineSegment((Point[]) lineSegmentPoints.toArray(new Point[0]), slope);

            if (!lineSegments.containsKey(lineSegmentToAdd.getLineIdentifier())) {
                lineSegments.put(lineSegmentToAdd.getLineIdentifier(), lineSegmentToAdd);
            }
        }
    }

    private static void drawLineSegments(Hashtable<String, LineSegment> lineSegments) {
        for (LineSegment lineSegment : lineSegments.values()) {
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
        private double slope;

        private LineSegment(Point[] points, double lineSlope) {
            lineSegmentPoints = points;
            Arrays.sort(lineSegmentPoints);

            slope = lineSlope;
        }

        private String getLineIdentifier() {
            // We just need to compute b in the equation y = mx + b
            // To do that we need the X and Y coordinates of any point
            // Unfortunately we can't add getX and getY to the Point class so we do that in a (in)glorious hack
            int x = getX(getPoints()[0]);
            int y = getY(getPoints()[0]);

            // Vertical line
            if (slope == Double.POSITIVE_INFINITY) {
                return "x = " + Integer.toString(x);
            }

            // Horizontal line
            if (slope == 0.0) {
                return "y = " + Integer.toString(y);
            }

            // Neither vertical nor horizontal line
            double b = y - slope * x;
            return "y = " + String.format("%.8f", slope) + " * x + " + String.format("%.8f", b);
        }

        private boolean contains(LineSegment that) {
            if (slope != that.slope) return false;
            if (that.getPoints().length >= this.getPoints().length) return false;


            // Since we know the slopes match, all we need to match here is the first point
            int thisPosition = 0;
            while (thisPosition < getPoints().length) {
                int comp = getPoints()[thisPosition].compareTo(that.getPoints()[0]);

                if (comp == 0) {
                    return true;
                }

                if (comp > 0) {
                    // No need to keep going as the points are in order
                    return false;
                }

                thisPosition++;
            }

            return false;

            /*
            int thatPosition = 0;
            int thisPosition = 0;
            while (thisPosition < getPoints().length
                    && thatPosition < that.getPoints().length
                    && (getPoints().length - thisPosition >= that.getPoints().length - thatPosition)) {
                if (getPoints()[thisPosition].compareTo(that.getPoints()[thatPosition]) == 0) {
                    thatPosition++;
                }
                thisPosition++;
            }
            return thatPosition == that.getPoints().length;
            */
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

        private static int getX(Point p) {
            String pointAsString = p.toString();  // (XX,YY)
            String xAsString = pointAsString.substring(1, pointAsString.indexOf(",")).trim();
            return Integer.valueOf(xAsString);
        }
        private static int getY(Point p) {
            String pointAsString = p.toString();  // (XX,YY)
            String yAsString = pointAsString.substring(pointAsString.indexOf(",") + 1, pointAsString.length() - 1).trim();
            return Integer.valueOf(yAsString);
        }
    }
}



/*
(10000, 0) -> (8000, 2000) -> (2000, 8000) -> (0, 10000)
(10000, 0) -> (13000, 0) -> (20000, 0) -> (30000, 0)
(30000, 0) -> (20000, 10000) -> (10000, 20000) -> (0, 30000)
(13000, 0) -> (11000, 3000) -> (9000, 6000) -> (5000, 12000)

(10000, 0) -> (8000, 2000) -> (2000, 8000) -> (0, 10000)
(10000, 0) -> (13000, 0) -> (20000, 0) -> (30000, 0)
(13000, 0) -> (11000, 3000) -> (9000, 6000) -> (5000, 12000)
*(18000, 2000) -> (9000, 6000) -> (0, 10000)
*(30000, 0) -> (9000, 6000) -> (2000, 8000)
(30000, 0) -> (20000, 10000) -> (10000, 20000) -> (0, 30000)
*(20000, 0) -> (18000, 2000) -> (2000, 18000)
 */