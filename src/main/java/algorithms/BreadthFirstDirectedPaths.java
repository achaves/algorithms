package algorithms; /*************************************************************************
 *  Compilation:  javac algorithms.BreadthFirstDirectedPaths.java
 *  Execution:    java algorithms.BreadthFirstDirectedPaths V E
 *  Dependencies: algorithms.Digraph.java Queue.java Stack.java
 *
 *  Run breadth first search on a digraph.
 *  Runs in O(E + V) time.
 *
 *  % java algorithms.BreadthFirstDirectedPaths tinyDG.txt 3
 *  3 to 0 (2):  3->2->0
 *  3 to 1 (3):  3->2->0->1
 *  3 to 2 (1):  3->2
 *  3 to 3 (0):  3
 *  3 to 4 (2):  3->5->4
 *  3 to 5 (1):  3->5
 *  3 to 6 (-):  not connected
 *  3 to 7 (-):  not connected
 *  3 to 8 (-):  not connected
 *  3 to 9 (-):  not connected
 *  3 to 10 (-):  not connected
 *  3 to 11 (-):  not connected
 *  3 to 12 (-):  not connected
 *
 *************************************************************************/

/**
 *  The <tt>BreadthDirectedFirstPaths</tt> class represents a data type for finding
 *  shortest paths (number of edges) from a source vertex <em>s</em>
 *  (or set of source vertices) to every other vertex in the digraph.
 *  <p>
 *  This implementation uses breadth-first search.
 *  The constructor takes time proportional to <em>V</em> + <em>E</em>,
 *  where <em>V</em> is the number of vertices and <em>E</em> is the number of edges.
 *  It uses extra space (not including the digraph) proportional to <em>V</em>.
 *  <p>
 *  For additional documentation, see <a href="/algs4/41graph">Section 4.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class BreadthFirstDirectedPaths {
    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path

    /**
     * Computes the shortest path from <tt>s</tt> and every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param s the source vertex
     */
    public BreadthFirstDirectedPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
        bfs(G, s);
    }

    /**
     * Computes the shortest path from any one of the source vertices in <tt>sources</tt>
     * to every other vertex in graph <tt>G</tt>.
     * @param G the digraph
     * @param sources the source vertices
     */
    public BreadthFirstDirectedPaths(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        distTo = new int[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); v++) distTo[v] = INFINITY;
        bfs(G, sources);
    }

    // BFS from single source
    private void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    // BFS from multiple sources
    private void bfs(Digraph G, Iterable<Integer> sources) {
        Queue<Integer> q = new Queue<Integer>();
        for (int s : sources) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue(s);
        }
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a directed path from the source <tt>s</tt> (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return <tt>true</tt> if there is a directed path, <tt>false</tt> otherwise
     */
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source <tt>s</tt>
     * (or sources) to vertex <tt>v</tt>?
     * @param v the vertex
     * @return the number of edges in a shortest path
     */
    public int distTo(int v) {
        return distTo[v];
    }

    /**
     * Returns a shortest path from <tt>s</tt> (or sources) to <tt>v</tt>, or
     * <tt>null</tt> if no such path.
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     */
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }

    /**
     * Unit tests the <tt>algorithms.BreadthFirstDirectedPaths</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        // StdOut.println(G);

        int s = Integer.parseInt(args[1]);
        BreadthFirstDirectedPaths bfs = new BreadthFirstDirectedPaths(G, s);

        for (int v = 0; v < G.V(); v++) {
            if (bfs.hasPathTo(v)) {
                StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
                for (int x : bfs.pathTo(v)) {
                    if (x == s) StdOut.print(x);
                    else        StdOut.print("->" + x);
                }
                StdOut.println();
            }

            else {
                StdOut.printf("%d to %d (-):  not connected\n", s, v);
            }

        }
    }


    /*************************************************************************
     *  Compilation:  javac algorithms.BreadthFirstDirectedPaths.RectHV.java
     *  Execution:    java algorithms.BreadthFirstDirectedPaths.RectHV
     *  Dependencies: Point2D.java
     *
     *  Implementation of 2D axis-aligned rectangle.
     *
     *************************************************************************/

    public static class RectHV {
        private final double xmin, ymin;   // minimum x- and y-coordinates
        private final double xmax, ymax;   // maximum x- and y-coordinates

        // construct the axis-aligned rectangle [xmin, xmax] x [ymin, ymax]
        public RectHV(double xmin, double ymin, double xmax, double ymax) {
            if (xmax < xmin || ymax < ymin) {
                throw new IllegalArgumentException("Invalid rectangle");
            }
            this.xmin = xmin;
            this.ymin = ymin;
            this.xmax = xmax;
            this.ymax = ymax;
        }

        // accessor methods for 4 coordinates
        public double xmin() { return xmin; }
        public double ymin() { return ymin; }
        public double xmax() { return xmax; }
        public double ymax() { return ymax; }

        // width and height of rectangle
        public double width()  { return xmax - xmin; }
        public double height() { return ymax - ymin; }

        // does this axis-aligned rectangle intersect that one?
        public boolean intersects(RectHV that) {
            return this.xmax >= that.xmin && this.ymax >= that.ymin
                    && that.xmax >= this.xmin && that.ymax >= this.ymin;
        }

        // draw this axis-aligned rectangle
        public void draw() {
            StdDraw.line(xmin, ymin, xmax, ymin);
            StdDraw.line(xmax, ymin, xmax, ymax);
            StdDraw.line(xmax, ymax, xmin, ymax);
            StdDraw.line(xmin, ymax, xmin, ymin);
        }

        // distance from p to closest point on this axis-aligned rectangle
        public double distanceTo(Point2D p) {
            return Math.sqrt(this.distanceSquaredTo(p));
        }

        // distance squared from p to closest point on this axis-aligned rectangle
        public double distanceSquaredTo(Point2D p) {
            double dx = 0.0, dy = 0.0;
            if      (p.x() < xmin) dx = p.x() - xmin;
            else if (p.x() > xmax) dx = p.x() - xmax;
            if      (p.y() < ymin) dy = p.y() - ymin;
            else if (p.y() > ymax) dy = p.y() - ymax;
            return dx*dx + dy*dy;
        }

        // does this axis-aligned rectangle contain p?
        public boolean contains(Point2D p) {
            return (p.x() >= xmin) && (p.x() <= xmax)
                    && (p.y() >= ymin) && (p.y() <= ymax);
        }

        // are the two axis-aligned rectangles equal?
        public boolean equals(Object y) {
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            RectHV that = (RectHV) y;
            if (this.xmin != that.xmin) return false;
            if (this.ymin != that.ymin) return false;
            if (this.xmax != that.xmax) return false;
            if (this.ymax != that.ymax) return false;
            return true;
        }

        // return a string representation of this axis-aligned rectangle
        public String toString() {
            return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
        }

    }
}
