import junit.framework.Assert;
import org.junit.Test;

import java.util.Comparator;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by allenc289 on 10/5/14.
 */
public class PointTests {

    @Test
    public void testCompareToSameYDifferentX1() {
        Point p1 = new Point(0, 2);
        Point p2 = new Point(1, 2);

        assert (p1.compareTo(p2) < 0);
    }

    @Test
    public void testCompareToSameYSameX() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);

        assert (p1.compareTo(p2) == 0);
    }

    @Test
    public void testCompareToSameYDifferentX2() {
        Point p1 = new Point(2, 2);
        Point p2 = new Point(1, 2);

        assert (p1.compareTo(p2) > 0);
    }

    @Test
    public void testCompareToSameXDifferentY1() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 3);

        assert (p1.compareTo(p2) < 0);
    }

    @Test
    public void testCompareToSameXDifferentY2() {
        Point p1 = new Point(1, 4);
        Point p2 = new Point(1, 3);

        assert (p1.compareTo(p2) > 0);
    }

    @Test
    public void testSlopeToHorizontalLine() {
        Point p1 = new Point(1, 4);
        Point p2 = new Point(2, 4);

        assertEquals(0.0, p1.slopeTo(p2));
    }

    @Test
    public void testSlopeToVerticalLine() {
        Point p1 = new Point(1, 4);
        Point p2 = new Point(1, 3);

        assertEquals(Double.POSITIVE_INFINITY, p1.slopeTo(p2));
    }

    @Test
    public void testSlopeToDegenerateLine() {
        Point p1 = new Point(1, 3);
        Point p2 = new Point(1, 3);

        assertEquals(Double.NEGATIVE_INFINITY, p1.slopeTo(p2));
    }

    @Test
    public void testSlopeEquidistant() {
        Point p1 = new Point(18000, 2000);
        Point p2 = new Point(9000, 6000);
        Point p3 = new Point(0, 10000);

        assertEquals(p1.slopeTo(p2), p1.slopeTo(p3));
    }

    /*
    @Test
    public void testRandomPoints() {
        for (int runs = 1; runs < 500; runs++) {
            // y = mx + b
            int b = StdRandom.uniform(100);
            int m = StdRandom.uniform(1, 10);

            Point[] points = new Point[4];
            for (int i = 0; i < 4; i++) {
                int x = StdRandom.uniform(10000);
                int y = m * x + b;
                points[i] = new Point(x, y);
            }

            assertEquals(1, Fast.getLineSegments(points).size());
        }
    }
    */

}
