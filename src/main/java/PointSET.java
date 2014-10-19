import java.util.ArrayList;
import java.util.List;

/**
 * Created by allenc289 on 10/16/14.
 */
public class PointSET {
    private SET<Point2D> bst = new SET<Point2D>();

    // construct an empty set of points
    public PointSET() {

    }

    // is the set empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points in the set
    public int size() {
        return bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        bst.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return bst.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : bst) {
            StdDraw.point(p.x(), p.y());
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> pointsInRect = new ArrayList<Point2D>();
        for (Point2D p : bst) {
            if (rect.contains(p)) {
                pointsInRect.add(p);
            }
        }
        return pointsInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        double minDistance = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        for (Point2D pInBST : bst) {
            if (Math.abs(p.distanceTo(pInBST)) < minDistance) {
                minDistance = p.distanceTo(pInBST);
                nearestPoint = pInBST;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
