import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by allenc289 on 10/11/14.
 */
public class Board {
    private int N;
    private int N_SQUARED;
    private char[] tiles;
    private int hammingNumber = -1;
    private int manhattanNumber = -1;
    private int emptyTile = -1;
    private ArrayList<Board> neighbors = null;
    private String boardAsString;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.tiles = int2dToChar1d(blocks);
        N_SQUARED = blocks.length * blocks.length;
        N = blocks.length;
    }

    private Board(char[] blocks, int manhattanDistance) {
        this.tiles = blocks;
        N_SQUARED = blocks.length;
        N = (int) Math.sqrt(blocks.length);
        manhattanNumber = manhattanDistance;
    }

    private char[] int2dToChar1d(int[][] blocks) {
        char[] chars = new char[blocks.length * blocks.length];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                chars[i * blocks.length + j] = (char) blocks[i][j];
            }
        }

        return chars;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        if (hammingNumber == -1) {
            hammingNumber = 0;
            for (int i = 0; i < N_SQUARED; i++) {
                if (tiles[i] != 0) {
                    int rightNumber = i + 1;
                    if (tiles[i] != rightNumber) {
                        hammingNumber++;
                    }
                }
            }
        }

        return hammingNumber;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        if (manhattanNumber == -1) {
            manhattanNumber = 0;
            for (int i = 0; i < N_SQUARED; i++) {
                if (tiles[i] != 0) {
                    manhattanNumber += calculateManhattanForTile(tiles[i], i);
                }
            }
        }

        return manhattanNumber;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        char[] twinTiles = cloneTiles();

        for (int i = 0; i < N; i++) {
            if (twinTiles[i * N] != 0 && twinTiles[i * N + 1] != 0) {
                swapTiles(twinTiles, i, 0, i, 1);
                return new Board(twinTiles, manhattan() + manhattanDelta(tiles[i * N], i, 0, i, 1) + manhattanDelta(tiles[i * N + 1], i, 1, i, 0));
            }
        }

        throw new RuntimeException("Board doesn't have empty tile");
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (that.N != this.N) return false;
        for (int i = 0; i < N_SQUARED; i++) {
            if (that.tiles[i] != this.tiles[i]) return false;
        }

        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (neighbors == null) {
            neighbors = new ArrayList<Board>();

            int emptyTileRow = getEmptyTile() / N;
            int emptyTileColumn = getEmptyTile() - emptyTileRow * N;

            // Top neighbor
            if (emptyTileRow != 0) {
                char[] topNeighbor = cloneTiles();
                swapTiles(topNeighbor, emptyTileRow, emptyTileColumn, emptyTileRow - 1, emptyTileColumn);
                neighbors.add(new Board(topNeighbor,
                        manhattan() + manhattanDelta(tiles[(emptyTileRow - 1) * N + emptyTileColumn],
                                emptyTileRow, emptyTileColumn, emptyTileRow - 1, emptyTileColumn)));
            }

            // Left neighbor
            if (emptyTileColumn != 0) {
                char[] leftNeighbor = cloneTiles();
                swapTiles(leftNeighbor, emptyTileRow, emptyTileColumn, emptyTileRow, emptyTileColumn - 1);

                neighbors.add(new Board(leftNeighbor,
                        manhattan() + manhattanDelta(tiles[emptyTileRow * N + emptyTileColumn - 1],
                                emptyTileRow, emptyTileColumn, emptyTileRow, emptyTileColumn - 1)));
            }

            // Right neighbor
            if (emptyTileColumn != N - 1) {
                char[] rightNeighbor = cloneTiles();
                swapTiles(rightNeighbor, emptyTileRow, emptyTileColumn, emptyTileRow, emptyTileColumn + 1);
                neighbors.add(new Board(rightNeighbor,
                        manhattan() + manhattanDelta(tiles[emptyTileRow * N + emptyTileColumn + 1],
                                emptyTileRow, emptyTileColumn, emptyTileRow, emptyTileColumn + 1)));
            }

            // Bottom neighbor
            if (emptyTileRow != N - 1) {
                char[] bottomNeighbor = cloneTiles();
                swapTiles(bottomNeighbor, emptyTileRow, emptyTileColumn, emptyTileRow + 1, emptyTileColumn);
                neighbors.add(new Board(bottomNeighbor,
                        manhattan() + manhattanDelta(tiles[(emptyTileRow + 1) * N + emptyTileColumn],
                                emptyTileRow, emptyTileColumn, emptyTileRow + 1, emptyTileColumn)));
            }
        }

        return neighbors;
    }



    // string representation of this board (in the output format specified below)
    public String toString() {
        if (boardAsString == null) {
            StringBuilder s = new StringBuilder();
            s.append(N + "\n");
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    s.append(String.format("%2d ", (int) tiles[i * N + j]));
                }
                s.append("\n");
            }

            boardAsString = s.toString();
        }
        return boardAsString;
    }

    private int getEmptyTile() {
        if (emptyTile == -1) {
            for (int i = 0; i < N_SQUARED; i++) {
                if (tiles[i] == 0) {
                    emptyTile = i;
                    return emptyTile;
                }
            }
        }
        return emptyTile;
    }

    private void swapTiles(char[] tileArray, int fromRow, int fromColumn, int toRow, int toColumn) {
        char temp = tileArray[fromRow * N + fromColumn];
        tileArray[fromRow * N + fromColumn] = tileArray[toRow * N + toColumn];
        tileArray[toRow * N + toColumn] = temp;
    }

    private char[] cloneTiles() {
        return Arrays.copyOf(tiles, tiles.length);
    }

    private int calculateManhattanForTile(char tile, int position) {
        int rightRow = (tile - 1) / N;
        int rightColumn = tile - rightRow * N - 1;

        int currentRow = position / N;
        int currentColumn = position - currentRow * N;

        int manhattanDistance = Math.abs(currentRow - rightRow);
        manhattanDistance += Math.abs(currentColumn - rightColumn);


        return manhattanDistance;
    }

    private int manhattanDelta(char tile, int fromRow, int fromColumn, int toRow, int toColumn) {
        return calculateManhattanForTile(tile, fromRow * N + fromColumn) - calculateManhattanForTile(tile, toRow * N + toColumn);
    }
}
