/*************************************************************************
 * Name: Sumy <br>
 * Email: sunmingjian7@gmail.com <br>
 * <br>
 * Compilation: javac Board.java <br>
 * Execution: <br>
 * Dependencies: <br>
 * <br>
 * Description: Store board.<br>
 * 
 *************************************************************************/

public class Board {
    private static final int[][] DIRECT = { { 0, 1 }, { 0, -1 }, { 1, 0 },
            { -1, 0 } };
    private char[][] mBlocks;
    private int N;

    private int xBlank;
    private int yBlank;
    private int nHamming;
    private int nManhattan;

    public Board(int[][] blocks) {
        N = blocks.length;
        mBlocks = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mBlocks[i][j] = (char) blocks[i][j];
                if (mBlocks[i][j] == 0) {
                    xBlank = i;
                    yBlank = j;
                }
            }
        }

        nHamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mBlocks[i][j] == 0)
                    continue;
                if (mBlocks[i][j] != convertPosition(i, j))
                    nHamming++;
            }
        }

        nManhattan = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (mBlocks[i][j] == 0)
                    continue;
                int x = getPositionX(mBlocks[i][j]);
                int y = getPositionY(mBlocks[i][j]);
                nManhattan += Math.abs(x - i) + Math.abs(y - j);
            }
        }
    }

    public int dimension() {
        return N;
    }

    public int hamming() {
        return nHamming;
    }

    public int manhattan() {
        return nManhattan;
    }

    public boolean isGoal() {
        return (nHamming == 0) && (nManhattan == 0);
    }

    public Board twin() {
        int[][] tBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tBlocks[i][j] = mBlocks[i][j];
            }
        }
        if (tBlocks[0][0] != 0 && tBlocks[0][1] != 0) {
            swap(tBlocks, 0, 0, 0, 1);
        } else {
            swap(tBlocks, 1, 0, 1, 1);
        }
        return new Board(tBlocks);
    }

    public boolean equals(Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        Board that = (Board) y;
        if (that.N != this.N)
            return false;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (that.mBlocks[i][j] != this.mBlocks[i][j])
                    return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        int[][] tBlocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tBlocks[i][j] = mBlocks[i][j];
            }
        }
        Stack<Board> neiStack = new Stack<Board>();
        for (int i = 0; i < 4; i++) {
            int newx = xBlank + DIRECT[i][0];
            int newy = yBlank + DIRECT[i][1];
            if (newx >= 0 && newx < N && newy >= 0 && newy < N) {
                swap(tBlocks, xBlank, yBlank, newx, newy);
                neiStack.push(new Board(tBlocks));
                swap(tBlocks, xBlank, yBlank, newx, newy);
            }
        }
        return neiStack;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", (int) mBlocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private int convertPosition(int x, int y) {
        return x * N + y + 1;
    }

    private int getPositionX(int position) {
        return (position - 1) / N;
    }

    private int getPositionY(int position) {
        return (position - 1) % N;
    }

    private static void swap(int[][] blocks, int x1, int y1, int x2, int y2) {
        int tmp = blocks[x1][y1];
        blocks[x1][y1] = blocks[x2][y2];
        blocks[x2][y2] = tmp;
    }
}