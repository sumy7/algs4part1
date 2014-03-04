public class PercolationStats {
        private double[] xThreshold;
        private double xMean = 0.0;
        private double xStddev = 0.0;
        private double xConfidenceLo = 0.0;
        private double xConfidenceHi = 0.0;

        public PercolationStats(int N, int T) {
                if ((N <= 0) || (T <= 0))
                        throw new java.lang.IllegalArgumentException();
                xThreshold = new double[T];
                for (int k = 0; k < T; k++) {
                        Percolation percolation = new Percolation(N);
                        int tryTimes = 0;
                        while (!percolation.percolates()) {
                                int nextX = StdRandom.uniform(N);
                                int nextY = StdRandom.uniform(N);
                                if (percolation.isOpen(nextX + 1, nextY + 1))
                                        continue;
                                percolation.open(nextX + 1, nextY + 1);
                                tryTimes++;
                        }
                        xThreshold[k] = 1.0 * tryTimes / N / N;
                }
                xMean = StdStats.mean(xThreshold);
                xStddev = StdStats.stddev(xThreshold);
                xConfidenceLo = xMean - 1.96 * xStddev / Math.sqrt(T);
                xConfidenceHi = xMean + 1.96 * xStddev / Math.sqrt(T);

        }

        public double mean() {
                return xMean;
        }

        public double stddev() {
                return xStddev;
        }

        public double confidenceLo() {
                return xConfidenceLo;
        }

        public double confidenceHi() {
                return xConfidenceHi;
        }

        public static void main(String[] args) {
                int N, T;
                N = Integer.parseInt(args[0]);
                T = Integer.parseInt(args[1]);
                PercolationStats percolationStats = new PercolationStats(N, T);
                Out out = new Out();
                out.println("mean                    = "
                                + percolationStats.mean());
                out.println("stddev                  = "
                                + percolationStats.stddev());
                out.println("95% confidence interval = "
                                + percolationStats.confidenceLo() + ", "
                                + percolationStats.confidenceHi());
        }
}
