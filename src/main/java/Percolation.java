/**
 * Created with IntelliJ IDEA.
 * User: allenc289
 * Date: 9/13/14
 * Time: 4:01 PM
 */
public class Percolation {
    private int gridDimension = 0;             // the grid will be gridDimension X gridDimension
    private SiteStatus[][] grid;

    private enum SiteStatus {
        Open,
        OpenAndFull
    }

    private WeightedQuickUnionUF uf;
    private int virtualTopSite;
    private int virtualBottomSite;


    /**
     * Constructs a Percolation object.
     * throws java.lang.IllegalArgumentException if either i/j < 1 or i/j > gridDimension
     *
     * @param gridSideSize the size of each side of the grid
     */
    public Percolation(int gridSideSize) {
        if (gridSideSize <= 0) {
            throw new IllegalArgumentException();
        }

        // create N-by-N grid, with all sites blocked
        int gridSize = gridSideSize * gridSideSize;
        gridDimension = gridSideSize;
        grid = new SiteStatus[gridDimension][gridDimension];

        // N X N + 2 sites (the two extra sites are a virtual top at position N*N and a virtual bottom at N*N+1
        uf = new WeightedQuickUnionUF(gridSize + 2);

        // Virtual top site is connected to first row
        virtualTopSite = gridSize;
        for (int i = 1; i <= gridSideSize; i++) {
            uf.union(virtualTopSite, siteNumberFromGridPosition(1, i));
        }

        // Virtual bottom site will be connected to last row as the sites there are open
        virtualBottomSite = gridSize + 1;
    }

    /**
     * Open the site at row i and column j.
     * throws java.lang.IndexOutOfBoundsException if either i/j < 1 or i/j > gridDimension
     *
     * @param i the row number
     * @param j the column number
     */
    public void open(int i, int j) {
        checkGridIndexes(i, j);

        // open site (row i, column j) if it is still blocked
        if (isBlocked(i, j)) {
            int siteNumber = siteNumberFromGridPosition(i, j);
            connectToNonBlockedNeighbors(i, j, siteNumber);
            if (i == gridDimension) {
                // Connect the site being open in the last row to the virtual bottom
                uf.union(virtualBottomSite, siteNumber);
            }

            // if this site is now connected to virtual top, make it 'full' and
            // flood neighbor sites recursively. There is a special case that
            // may happen with new connections after the grid has percolated
            // (called backwash).
            if (uf.connected(virtualTopSite, siteNumber) && doesNotCauseBackwash(i, j)) {
                setGridValue(i, j, SiteStatus.OpenAndFull);
                floodOpenNeighbors(i, j);
            } else {
                // Otherwise just mark it as open
                setGridValue(i, j, SiteStatus.Open);
            }
        }
    }

    /**
     * Checks if the site at row i and column j is open (full or not).
     * throws java.lang.IndexOutOfBoundsException if either i/j < 1 or i/j > gridDimension
     *
     * @param i the row number
     * @param j the column number
     * @return boolean
     */
    public boolean isOpen(int i, int j) {
        checkGridIndexes(i, j);

        // is site (row i, column j) open?
        return getGridValue(i, j) == SiteStatus.Open
                || getGridValue(i, j) == SiteStatus.OpenAndFull;
    }

    /**
     * Checks of the site at row i and column j is full.
     * throws java.lang.IndexOutOfBoundsException if either i/j < 1 or i/j > gridDimension
     *
     * @param i the row number
     * @param j the column number
     * @return boolean
     */
    public boolean isFull(int i, int j) {
        checkGridIndexes(i, j);
        return getGridValue(i, j) == SiteStatus.OpenAndFull;
    }

    /**
     * Checks if the grid percolates (there is a path between top and bottom)
     */
    public boolean percolates() {
        return uf.connected(virtualTopSite, virtualBottomSite);
    }

    //
    // Private
    //

    private boolean isBlocked(int i, int j) {
        return getGridValue(i, j) == null;
    }

    private boolean isJustOpen(int i, int j) {
        return getGridValue(i, j) == SiteStatus.Open;
    }

    private SiteStatus getGridValue(int row, int column) {
        return grid[row - 1][column - 1];
    }

    private void setGridValue(int row, int column, SiteStatus value) {
        grid[row - 1][column - 1] = value;
    }

    private void connectToNonBlockedNeighbors(int i, int j, int siteNumber) {
        // Top
        if (i > 1 && !isBlocked(i - 1, j)) {
            uf.union(siteNumber, siteNumberFromGridPosition(i - 1, j));
        }
        // Bottom
        if (i < gridDimension && !isBlocked(i + 1, j)) {
            uf.union(siteNumber, siteNumberFromGridPosition(i + 1, j));
        }
        // Left
        if (j > 1 && !isBlocked(i, j - 1)) {
            uf.union(siteNumber, siteNumberFromGridPosition(i, j - 1));
        }
        // Right
        if (j < gridDimension && !isBlocked(i, j + 1)) {
            uf.union(siteNumber, siteNumberFromGridPosition(i, j + 1));
        }
    }

    private void floodOpenNeighbors(int i, int j) {
        // Top
        if (i > 1 && isJustOpen(i - 1, j)) {
            setGridValue(i - 1, j, SiteStatus.OpenAndFull);
            floodOpenNeighbors(i - 1, j);
        }
        // Bottom
        if (i < gridDimension && isJustOpen(i + 1, j)) {
            setGridValue(i + 1, j, SiteStatus.OpenAndFull);
            floodOpenNeighbors(i + 1, j);
        }
        // Left
        if (j > 1 && isJustOpen(i, j - 1)) {
            setGridValue(i, j - 1, SiteStatus.OpenAndFull);
            floodOpenNeighbors(i, j - 1);
        }
        // Right
        if (j < gridDimension && isJustOpen(i, j + 1)) {
            setGridValue(i, j + 1, SiteStatus.OpenAndFull);
            floodOpenNeighbors(i, j + 1);
        }
    }


    private boolean doesNotCauseBackwash(int i, int j) {
        if (i != 1) {
            // If not the first row, site needs to be connected to an already
            // full site in order to not cause backwash.

            // Top
            if (i > 1 && isFull(i - 1, j)) {
                return true;
            }
            // Bottom
            if (i < gridDimension && isFull(i + 1, j)) {
                return true;
            }
            // Left
            if (j > 1 && isFull(i, j - 1)) {
                return true;
            }
            // Right
            if (j < gridDimension && isFull(i, j + 1)) {
                return true;
            }

            return false;
       }

       return true;
    }

    private void checkGridIndexes(int i, int j) {
        if (i <= 0 || i > gridDimension) {
            throw new IndexOutOfBoundsException();
        }
        if (j <= 0 || i > gridDimension) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int siteNumberFromGridPosition(int row, int column) {
        return (row - 1) * gridDimension + column - 1;
    }
}
