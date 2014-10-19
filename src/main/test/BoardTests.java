import junit.framework.Assert;
import org.junit.Test;

import java.io.Console;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by allenc289 on 10/9/14.
 */
public class BoardTests {
    @Test
    public void testBoardCreate() {
        int[][] tiles = getTiles();

        Board b = new Board(tiles);
        StdOut.println(b.toString());
        assertEquals("3\n" + " 1  4  7 \n" + " 2  5  8 \n" + " 3  6  0 \n", b.toString());
        assertEquals(3, b.dimension());
        assertEquals(6, b.hamming());
        assertEquals(16, b.manhattan());
        assertEquals(false, b.isGoal());

        int[][] tiles2 = getTiles();
        assertEquals(true, b.equals(new Board(tiles2)));
    }

    @Test
    public void testNeighbors1() {
        Board b = new Board(getTiles());

        int numberOfNeighbors = getNumberOfNeighbors(b);
        assertEquals(2, numberOfNeighbors);
    }

    @Test
    public void testNeighbors2() {
        int[][] tiles = getTiles();
        tiles[2][2] = tiles[2][1];
        tiles[2][1] = 0;

        Board b = new Board(tiles);

        int numberOfNeighbors = getNumberOfNeighbors(b);
        assertEquals(3, numberOfNeighbors);
    }

    @Test
    public void testNeighbors3() {
        int[][] tiles = getTiles();
        tiles[2][2] = tiles[1][1];
        tiles[1][1] = 0;

        Board b = new Board(tiles);

        int numberOfNeighbors = getNumberOfNeighbors(b);
        assertEquals(4, numberOfNeighbors);
    }

    @Test
    public void testNeighbors4() {
        int[][] tiles = getTiles();
        tiles[2][1] = tiles[0][0];
        tiles[0][0] = 0;

        Board b = new Board(tiles);

        int numberOfNeighbors = getNumberOfNeighbors(b);
        assertEquals(2, numberOfNeighbors);
    }

    private int getNumberOfNeighbors(Board b) {
        int numberOfNeighbors = 0;
        for (Board n : b.neighbors()) {
            numberOfNeighbors++;
            //StdOut.println(n.toString());
        }
        return numberOfNeighbors;
    }

    private int[][] getTiles() {
        int[][] tiles = new int[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               tiles[i][j] = (j * 3 + i + 1);
            }
        }
        tiles[2][2] = 0;
        return tiles;
    }

    @Test
    public void testScores() {
        int[][] tiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board b = new Board(tiles);
        assertEquals("3\n" + " 8  1  3 \n" + " 4  0  2 \n" + " 7  6  5 \n", b.toString());
        assertEquals(3, b.dimension());
        assertEquals(5, b.hamming());
        assertEquals(10, b.manhattan());

    }
}
