import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: allenc289
 * Date: 9/16/14
 * Time: 11:27 AM
 */
public class PercolationTests {
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
         new Percolation(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsOpenExceptionI() {
        new Percolation(9).isOpen(0, 1);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsOpenExceptionJ() {
        new Percolation(9).isOpen(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsFullExceptionI() {
        new Percolation(9).isFull(0, 1);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testIsFullExceptionJ() {
        new Percolation(9).isFull(1, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpenExceptionI() {
        new Percolation(9).open(-1, 1);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpenExceptionJ() {
        new Percolation(9).open(1, -1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOpenExceptionJ2() {
        new Percolation(9).open(1, 10);
    }

    @Test
    public void testPercolation1() {
        Percolation model = new Percolation(3);
        model.open(1, 1);
        assert(model.isFull(1, 1));
        assert(!model.percolates());

        model.open(2, 1);
        assert(model.isFull(2, 1));
        assert(!model.percolates());

        model.open(3, 1);
        assert(model.isFull(3, 1));
        assert(model.percolates());
    }

    @Test
    public void testPercolation2() {
        Percolation model = new Percolation(3);
        model.open(1, 1);
        assert(model.isFull(1, 1));
        assert(!model.percolates());

        model.open(2, 1);
        assert(model.isFull(2, 1));
        assert(!model.percolates());

        model.open(2, 2);
        assert(model.isFull(2, 2));
        assert(!model.percolates());

        model.open(3, 2);
        assert(model.isFull(3, 2));
        assert(model.percolates());
    }

    @Test
    public void testPercolation3() {
        Percolation model = new Percolation(3);
        model.open(1, 3);
        assert(model.isFull(1, 3));
        assert(!model.percolates());

        model.open(2, 3);
        assert(model.isFull(2, 3));
        assert(!model.percolates());

        model.open(3, 3);
        assert(model.isFull(3, 3));
        assert(model.percolates());

        model.open(3, 1);
        assert(!model.isFull(3, 1));
        assert(model.percolates());

        model.open(2, 1);
        assert(!model.isFull(2, 1));

        model.open(1, 1);
        assert(model.isFull(1, 1));
        assert(model.isFull(2, 1));
        assert(model.isFull(3, 1));
    }

    @Test
    public void testPercolation4() {
        Percolation model = new Percolation(1);
        //model.open(1, 1);
        assert(!model.percolates());
    }
}
