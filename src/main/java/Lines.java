import java.util.ArrayList;
import java.util.List;

/**
 * Created by allenc289 on 10/16/14.
 */
public class Lines {

    public static void main(String[] args) {
        List<Point2D> points = getPoints("A (11,  4)  ->  (19,  4)  [ horizontal ]\n" +
                "B ( 5,  0)  ->  (18,  0)  [ horizontal ]\n" +
                "C ( 6,  5)  ->  ( 6, 12)  [ vertical   ]\n" +
                "D ( 8,  9)  ->  (17,  9)  [ horizontal ]\n" +
                "E ( 9,  3)  ->  (16,  3)  [ horizontal ]\n" +
                "F (12, 11)  ->  (12, 13)  [ vertical   ]\n" +
                "G ( 0,  7)  ->  ( 0, 14)  [ vertical   ]\n" +
                "H (13,  6)  ->  (15,  6)  [ horizontal ]");

        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);

        for (int i = 0; i < points.size(); i +=2) {
            StdDraw.line(points.get(i).x(), points.get(i).y(), points.get(i + 1).x(), points.get(i + 1).y());
            StdDraw.text(points.get(i + 1).x(), points.get(i + 1).y(), points.get(i).toString() + " " + points.get(i + 1).toString());
        }

    }

    private static List<Point2D> getPoints(String ss) {
        ArrayList<Point2D> ps = new ArrayList<Point2D>();

        for (String s : ss.split("\n")) {
            s = s.substring(3);
            s = s.replace(")  [ horizontal ]", "");
            s = s.replace(")  [ vertical   ]", "");

            String[] points = s.split(" -> ");

            String[] pointA_xy = points[0].split(",");
            String[] pointB_xy = points[1].split(",");

            ps.add(new Point2D(Double.valueOf(pointA_xy[0]), Double.valueOf(pointA_xy[1].substring(0, pointA_xy[1].length() - 2))));
            ps.add(new Point2D(Double.valueOf(pointB_xy[0].substring(2)), Double.valueOf(pointB_xy[1])));
        }

        return ps;
    }
}
