public class Percolation {

        private WeightedQuickUnionUF weightedUF = null;
        private WeightedQuickUnionUF weightedUFBackwash = null;
        private int squareN = 0;
        private boolean[][] openedSquare;

        public Percolation(int N) {
                weightedUF = new WeightedQuickUnionUF(N * N + 2);
                weightedUFBackwash = new WeightedQuickUnionUF(N * N + 1);
                squareN = N;
                openedSquare = new boolean[N + 1][N + 1];
                for (int i = 0; i <= N; i++) {
                        for (int j = 0; j <= N; j++) {
                                openedSquare[i][j] = false;
                        }
                }
        }

        public void open(int i, int j) {
                if ((i <= 0) || (i > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                if ((j <= 0) || (j > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                openedSquare[i][j] = true;
                int openNum = convertCoordinate(i, j);
                if (i == 1) {
                        weightedUFBackwash.union(0, openNum);
                        weightedUF.union(0, openNum);
                }
                if (i == squareN) {
                        weightedUF.union(openNum, squareN * squareN + 1);
                }

                unionBlock(openNum, i - 1, j);
                unionBlock(openNum, i + 1, j);
                unionBlock(openNum, i, j - 1);
                unionBlock(openNum, i, j + 1);

        }

        public boolean isOpen(int i, int j) {
                if ((i <= 0) || (i > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                if ((j <= 0) || (j > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                return openedSquare[i][j];
        }

        public boolean isFull(int i, int j) {
                if ((i <= 0) || (i > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                if ((j <= 0) || (j > squareN))
                        throw new java.lang.IndexOutOfBoundsException();
                return weightedUFBackwash.connected(0, convertCoordinate(i, j));
        }

        public boolean percolates() {
                return weightedUF.connected(0, squareN * squareN + 1);
        }

        private int convertCoordinate(int i, int j) {
                return (i - 1) * squareN + j;
        }

        private void unionBlock(int s, int i, int j) {
                if ((i <= 0) || (i > squareN))
                        return;
                if ((j <= 0) || (j > squareN))
                        return;
                if (!openedSquare[i][j])
                        return;
                int newNum = convertCoordinate(i, j);
                weightedUF.union(s, newNum);
                weightedUFBackwash.union(s, newNum);
        }
}
