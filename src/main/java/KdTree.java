import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KdTree {

    private enum Orientation {
        HORIZONTAL,
        VERTICAL
    }

    private static class Node {
        private Point2D point; // the point
        private RectHV rect; // the axis-aligned rectangle corresponding to this node
        private Node left; // the left/bottom subtree
        private Node right; // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.point = p;
            this.rect = rect;
        }
    }

    private Node root;
    private Point2D nearest;
    private int size = 0;

    public boolean isEmpty() {
        // is the set empty?
        return root == null;
    }

    public int size() {
        // number of points in the set
        return size;
    }

    public void insert(Point2D p) {
        // add the point p to the set (if it is not already in the set)
        root = insert(root, null, p, Orientation.HORIZONTAL);
    }

    public boolean contains(Point2D p) {
        // does the set contain the point p?
        return get(root, p, Orientation.HORIZONTAL) != null;
    }

    public void draw() {
        // draw all of the points to standard draw
        drawPoint(root, null, Orientation.HORIZONTAL);
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points in the set that are inside the rectangle
        Set<Point2D> result = new HashSet<Point2D>();
        rangeSearch(root, rect, result);
        return result;
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        if (root == null) return null;

        nearest = root.point;
        nearestSearch(root, p, Orientation.HORIZONTAL);
        return nearest;
    }

    /*
             Private
     */
    private Node insert(Node node, Node parentNode, Point2D p, Orientation orientation) {
        if (node == null) {
            size++;
            double rectXmin = 0.0;
            double rectXmax = Double.MAX_VALUE;
            double rectYmin = 0.0;
            double rectYmax = Double.MAX_VALUE;
            if (parentNode != null) {
                if (Orientation.HORIZONTAL == orientation) {
                    rectXmin = parentNode.rect.xmin();
                    rectXmax = parentNode.rect.xmax();
                    if (p.y() < parentNode.point.y()) {
                        rectYmin = parentNode.rect.ymin();
                        rectYmax = parentNode.point.y();
                    } else if (p.y() > parentNode.point.y()) {
                        rectYmin = parentNode.point.y();
                        rectYmax = parentNode.rect.ymax();
                    }
                } else {
                    rectYmax = parentNode.rect.ymax();
                    rectYmin = parentNode.rect.ymin();
                    if (p.x() < parentNode.point.x()) {
                        rectXmin = parentNode.rect.xmin();
                        rectXmax = parentNode.point.x();
                    } else if (p.x() > parentNode.point.x()) {
                        rectXmin = parentNode.point.x();
                        rectXmax = parentNode.rect.xmax();
                    }
                }
            }
            return new Node(p, new RectHV(rectXmin, rectYmin, rectXmax, rectYmax));
        }
        double newKey = getXIfHorizontalYOtherwise(p, orientation);
        double nodeKey = getXIfHorizontalYOtherwise(node.point, orientation);
        if (newKey < nodeKey) {
            node.left = insert(node.left, node, p, nextOrientation(orientation));
        } else if (newKey > nodeKey) {
            node.right = insert(node.right, node, p, nextOrientation(orientation));
        } else {
            double newValue = getYIfHorizontalXOtherwise(p, orientation);
            double nodeValue = getYIfHorizontalXOtherwise(node.point, orientation);
            if (newValue == nodeValue) {
                node.point = p;
            } else {
                node.right = insert(node.right, node, p, nextOrientation(orientation));
            }
        }
        return node;
    }

    private Orientation nextOrientation(Orientation orientation) {
        if (orientation == Orientation.HORIZONTAL) return Orientation.VERTICAL;
        else return Orientation.HORIZONTAL;
    }

    private Point2D get(Node node, Point2D p, Orientation orientation) {
        if (node == null)
            return null;
        double newKey = getXIfHorizontalYOtherwise(p, orientation);
        double nodeKey = getXIfHorizontalYOtherwise(node.point, orientation);
        if (newKey < nodeKey) {
            return get(node.left, p, nextOrientation(orientation));
        } else if (newKey > nodeKey) {
            return get(node.right, p, nextOrientation(orientation));
        } else {
            double newValue = getYIfHorizontalXOtherwise(p, orientation);
            double nodeValue = getYIfHorizontalXOtherwise(node.point, orientation);
            if (newValue == nodeValue) {
                return node.point;
            } else {
                return get(node.right, p, nextOrientation(orientation));
            }
        }
    }

    private double getYIfHorizontalXOtherwise(Point2D p, Orientation orient) {
        if (Orientation.HORIZONTAL == orient) {
            return p.y();
        } else {
            return p.x();
        }
    }

    private double getXIfHorizontalYOtherwise(Point2D p, Orientation orient) {
        if (Orientation.HORIZONTAL == orient) {
            return p.x();
        } else {
            return p.y();
        }
    }

    private void drawPoint(Node node, Node parentNode, Orientation orientation) {
        if (node == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.point.draw();

        StdDraw.setPenRadius();
        if (orientation == Orientation.HORIZONTAL) {
            StdDraw.setPenColor(StdDraw.RED);
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
        }
        if (Orientation.HORIZONTAL == orientation) {
            if (parentNode != null) {
                if (node.point.y() < parentNode.point.y()) {
                    new Point2D(node.point.x(), 0.0).drawTo(new Point2D(
                            node.point.x(), parentNode.point.y()));
                } else if (node.point.y() > parentNode.point.y()) {
                    new Point2D(node.point.x(), parentNode.point.y())
                            .drawTo(new Point2D(node.point.x(), 1.0));
                }
            } else {
                new Point2D(node.point.x(), 0.0).drawTo(new Point2D(node.point
                        .x(), 1.0));
            }
        } else {
            if (parentNode != null) {
                if (node.point.x() < parentNode.point.x()) {
                    new Point2D(0.0, node.point.y()).drawTo(new Point2D(
                            parentNode.point.x(), node.point.y()));
                } else if (node.point.x() > parentNode.point.x()) {
                    new Point2D(parentNode.point.x(), node.point.y())
                            .drawTo(new Point2D(1.0, node.point.y()));
                }
            } else {
                new Point2D(0.0, node.point.y()).drawTo(new Point2D(1.0,
                        node.point.y()));
            }
        }
        drawPoint(node.left, node, nextOrientation(orientation));
        drawPoint(node.right, node, nextOrientation(orientation));
    }

    private void rangeSearch(Node node, RectHV rect, Set<Point2D> result) {
        if (node == null || !rect.intersects(node.rect)) {
            return;
        }
        if (rect.contains(node.point)) {
            result.add(node.point);
        }
        rangeSearch(node.left, rect, result);
        rangeSearch(node.right, rect, result);
    }


    private void nearestSearch(Node node, Point2D queryPoint, Orientation orientation) {
        if (node == null) {
            return;
        }
        double distanceToFoundSoFar = nearest.distanceSquaredTo(queryPoint);
        double distanceToCurNodeRect = node.rect.distanceSquaredTo(queryPoint);
        if (distanceToFoundSoFar < distanceToCurNodeRect) {
            return;
        } else {
            if (nearest.distanceSquaredTo(queryPoint) > node.point.distanceSquaredTo(queryPoint)) {
                nearest = node.point;
            }
            Node firstNode = null;
            Node secondNode = null;
            if (orientation == Orientation.HORIZONTAL) {
                if (queryPoint.x() < node.point.x()) {
                    firstNode = node.left;
                    secondNode = node.right;
                } else {
                    firstNode = node.right;
                    secondNode = node.left;
                }
            } else {
                if (queryPoint.y() < node.point.y()) {
                    firstNode = node.left;
                    secondNode = node.right;
                } else {
                    firstNode = node.right;
                    secondNode = node.left;
                }
            }
            nearestSearch(firstNode, queryPoint, nextOrientation(orientation));
            nearestSearch(secondNode, queryPoint, nextOrientation(orientation));
        }
    }

    /**
     * **************************************************************************
     * Test client
     * ***************************************************************************
     */
    public static void main(String[] args) {

        KdTree kd = new KdTree();

        for (Point2D p : getPoints()) {
            kd.insert(p);
        }

        kd.levelVisit();
    }

    private static Collection<Point2D> getPoints() {
        ArrayList<Point2D> points = new ArrayList<Point2D>();

        /*
        String ss = "A (0.05, 0.52)\n" +

                "B (0.53, 0.70)\n" +
                "C (0.31, 0.88)\n" +
                "D (0.26, 0.27)\n" +
                "E (0.55, 0.25)\n" +
                "F (0.15, 0.94)\n" +
                "G (0.96, 0.64)\n" +
                "H (0.75, 0.29)";

        for (String s : ss.split("\n")) {
            s = s.substring(3);
            s = s.substring(0, s.length() - 1);
            String[] xy = s.split(", ");

            points.add(new Point2D(Double.valueOf(xy[0]), Double.valueOf(xy[1])));
        }
*/
        return points;
    }

    private void levelVisit() {
        Queue<Node> q = new Queue<Node>();
        q.enqueue(root);

        while (!q.isEmpty()) {
            Node node = q.dequeue();
            //StdOut.print(node.val + " (" + isRed(node) + ") ");
            StdOut.print(node.point + " ");

            if (node.left != null) q.enqueue(node.left);
            if (node.right != null) q.enqueue(node.right);
        }
    }
}