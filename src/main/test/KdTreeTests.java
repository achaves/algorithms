import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by allenc289 on 10/16/14.
 */
public class KdTreeTests {
    @Test
    public void testNearest() {

        for (int k = 0; k < 1000; k++) {
            KdTree kd = new KdTree();
            for (int i = 0; i < 10000; i++) {
                kd.insert(new Point2D(StdRandom.uniform(0.0, 100.0), StdRandom.uniform(0.0, 100.0)));
            }

            Point2D p = kd.nearest(new Point2D(StdRandom.uniform(0.0, 100.0), StdRandom.uniform(0.0, 100.0)));


            assertNotNull(p);
        }
    }

    @Test
    public void testNearest2() {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.8347341304084863, 0.7803694503729777));
        tree.insert(new Point2D(0.8918827344457333, 0.0930259708427833));
        tree.insert(new Point2D(0.6186560394208392, 0.8241669463230892));
        tree.insert(new Point2D(0.650072335821017, 0.7234609897173627));
        tree.insert(new Point2D(0.9464495254952575, 0.8043453624772783));
        tree.insert(new Point2D(0.07093295559894774, 0.3050505680764969));
        tree.insert(new Point2D(0.029212661785887994, 0.9786427784140328));
        tree.insert(new Point2D(0.78535646229445, 0.8253331512225461));
        tree.insert(new Point2D(0.7625130905913755, 0.5970121210947144));
        tree.insert(new Point2D(0.16708937142345937, 0.45423319375977145));

        Point2D p = tree.nearest(new Point2D(0.9346044596234224, 0.4859832156723002));

        assertEquals(new Point2D(0.7625130905913755, 0.5970121210947144), p);
    }
}
