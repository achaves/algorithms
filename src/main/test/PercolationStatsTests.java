import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: allenc289
 * Date: 9/16/14
 * Time: 11:27 AM
 */
public class PercolationStatsTests {
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor1() {
         new PercolationStats(-1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor2() {
        new PercolationStats(1, -1);
    }

}
