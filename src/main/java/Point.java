/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // Compare points by the slopes they make with the invoking point (x0, y0).
    // Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
    // is less than the slope (y2 − y0) / (x2 − x0).
    // Treats horizontal, vertical, and degenerate line segments as in the slopeTo() method.
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point o1, Point o2) {
            double o1Slope = slopeTo(o1);
            double o2Slope = slopeTo(o2);
            if (o1Slope < o2Slope) {
                return -1;
            }
            else {
                if (o1Slope > o2Slope) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        }
    };


    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // the slope between the invoking point (x0, y0) and the argument point (x1, y1),
        // which is given by the formula (y1 − y0) / (x1 − x0).
        // Treats the slope of a horizontal line segment as positive zero;
        // Treats the slope of a vertical line segment as positive infinity;
        // Treats the slope of a degenerate line segment (between a point and itself) as negative infinity.
        if (that.y == this.y) {
            if (that.x == this.x) {
                return Double.NEGATIVE_INFINITY; // degenerate
            }
            else {
                return +0.0;  // horizontal line
            }
        }
        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY; // vertical line. Note that that.y != this.y because of if above
        }

        double slope = ((double) (that.y - this.y)) / (that.x - this.x);
        return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (that.y < this.y) {
            return 1;
        }
        if (that.y > this.y) {
            return -1;
        }

        // that.y == this.y at this point
        if (that.x < this.x) {
           return 1;
        }
        else {
            if (that.x > this.x) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}