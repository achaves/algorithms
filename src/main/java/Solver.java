import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by allenc289 on 10/11/14.
 */
public class Solver {
    private int dequeues;
    private int enqueues;
    private LinkedList<Board> solutionPath;
    private SearchNode solutionNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        solutionNode = aStar(initial);
        if (solutionNode != null) {
            solutionPath = new LinkedList<Board>();
            SearchNode node = solutionNode;
            while (node != null) {
                solutionPath.add(0, node.board);
                node = node.parentNode;
            }
        }
    }

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode parentNode;
        private int numberOfMovesSoFar;

        private SearchNode(Board b, SearchNode parentNode) {
            board = b;
            this.parentNode = parentNode;
            if (parentNode == null) {
                numberOfMovesSoFar = 0;
            } else {
                numberOfMovesSoFar = parentNode.numberOfMovesSoFar + 1;
            }
        }

        @Override
        public int compareTo(SearchNode o) {
            int cmp = (board.manhattan() + numberOfMovesSoFar) - (o.board.manhattan() + o.numberOfMovesSoFar);
            if (cmp == 0) {
                cmp = board.manhattan() - o.board.manhattan();
                if (cmp == 0) {
                    cmp = board.hamming() - o.board.hamming();
                }
            }
            return cmp;

        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (o == null) return false;
            if (o.getClass() != this.getClass()) return false;

            return ((SearchNode) o).board.equals(board);
        }

        @Override
        public int hashCode() {
            return board.toString().hashCode();
        }
    }

    private SearchNode aStar(Board initial) {
        SearchNode searchNode = new SearchNode(initial, null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        pq.insert(searchNode);

        HashSet<SearchNode> openSet = new HashSet<SearchNode>(); // The set of tentative nodes to be evaluated, initially containing the start node
        openSet.add(searchNode);

        while (openSet.size() > 0) {
            //print(pq);
            SearchNode currentNode = pq.delMin();
            dequeues++;

            if (currentNode.board.hamming() == 1 && currentNode.board.manhattan() == 2) {
                // unsolvable
                return null;
            }

            if (currentNode.board.isGoal()) {
                // We found the solution
                return currentNode;
            }

            // remove current from openset
            openSet.remove(currentNode);

            // Loop through neighbors and add them to openSet
            for (Board neighbor : currentNode.board.neighbors()) {
                SearchNode neighborNode = new SearchNode(neighbor, currentNode);
                if (currentNode.parentNode == null || !neighborNode.board.equals(currentNode.parentNode.board)) {
                    openSet.add(neighborNode);
                    pq.insert(neighborNode);
                    enqueues++;
                }
            }
        }

        return null; // unsolvable
    }

    private void print(MinPQ<SearchNode> pq) {
        StdOut.println("===============================================");
        for (SearchNode node : pq) {
            StdOut.println("Queued= " + enqueues);
            StdOut.println("Dequeued= " + dequeues);
            StdOut.println("Priority= " + (node.numberOfMovesSoFar + node.board.manhattan()));
            StdOut.println("Moves= " + node.numberOfMovesSoFar);
            StdOut.println("Manhatan= " + node.board.manhattan());
            StdOut.println(node.board);
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solutionNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return solutionNode.numberOfMovesSoFar;
        } else {
            return -1;
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        return solutionPath;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        /*
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        */

        String[] a = {"gold", "ceil", "ecru", "navy", "pink", "pear", "lust", "wine", "palm", "pine", "dust", "silk",
        "sand", "buff", "coal", "herb", "drab", "rose", "bone", "gray", "cyan", "dusk", "ruby", "plum"};

        Heap.sort(a);

        StdOut.println(a);

    }
}
