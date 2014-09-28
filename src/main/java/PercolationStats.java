/**
 * Created with IntelliJ IDEA.
 * User: allenc289
 * Date: 9/13/14
 * Time: 4:02 PM
 */
public class PercolationStats {
    private static final double PERCENTILE_95 = 1.96;
    private double[] percolationThresholds;
    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        int totalSites = N * N;

        // perform T independent computational experiments on an N-by-N grid
        percolationThresholds = new double[T];

        for (int experiment = 0; experiment < T; experiment++) {
            Percolation model = new Percolation(N);

            percolationThresholds[experiment] = 0;
            while (!model.percolates()) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;
                if (!model.isOpen(i, j) && !model.isFull(i, j)) {
                    percolationThresholds[experiment]++;
                    model.open(i, j);
                }
            }
            percolationThresholds[experiment] = percolationThresholds[experiment] / totalSites;
        }

        calculateStats();
    }

    private void calculateStats() {
        mean = StdStats.mean(percolationThresholds);
        stddev = StdStats.stddev(percolationThresholds);
        confidenceLo = mean - PERCENTILE_95 * stddev / Math.sqrt(percolationThresholds.length);
        confidenceHi = mean + PERCENTILE_95 * stddev / Math.sqrt(percolationThresholds.length);
    }

    public double mean() {
        // sample mean of percolation threshold
        return mean;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return stddev;
    }

    public double confidenceLo() {
        // returns lower bound of the 95% confidence interval
        return confidenceLo;
    }


    public double confidenceHi() {
        // returns upper bound of the 95% confidence interval
        return confidenceHi;
    }

    public static void main(String[] args) {
        // test client, described below
        int N = Integer.valueOf(args[0]);
        int T = Integer.valueOf(args[1]);

        PercolationStats stats = new PercolationStats(N, T);
        StdOut.print("mean                    = ");
        StdOut.println(stats.mean());
        StdOut.print("stddv                   = ");
        StdOut.println(stats.stddev());
        StdOut.print("95% confidence interval = ");
        StdOut.print(stats.confidenceLo());
        StdOut.print(", ");
        StdOut.print(stats.confidenceHi());
    }
}
