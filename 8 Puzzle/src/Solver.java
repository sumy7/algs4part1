/*************************************************************************
 * Name: Sumy <br>
 * Email: sunmingjian7@gmail.com<br>
 * <br>
 * Compilation: javac Solver.java <br>
 * Execution: <br>
 * Dependencies: Board.java<br>
 * <br>
 * Description: Solve a board puzzle and get solver step.<br>
 * 
 *************************************************************************/

public class Solver {
    private boolean isSolved = false;
    private int moveNum = 0;
    private Stack<Board> ansStack;

    private static class SearchTreeNode implements Comparable<SearchTreeNode> {
        private Board mBoard;
        private int manhattan;
        private int moves;
        private SearchTreeNode mParent;

        public SearchTreeNode(Board board, int move, SearchTreeNode parent) {
            this.mBoard = board;
            this.manhattan = board.manhattan();
            this.moves = move;
            this.mParent = parent;
            if (parent == null) {
                this.mParent = this;
            }
        }

        @Override
        public int compareTo(SearchTreeNode that) {
            if (this.manhattan + this.moves < that.manhattan + that.moves)
                return -1;
            if (this.manhattan + this.moves > that.manhattan + that.moves)
                return 1;
            return 0;
        }

    }

    public Solver(Board initial) {
        Board twin = initial.twin();
        isSolved = false;
        MinPQ<SearchTreeNode> minPQ = new MinPQ<SearchTreeNode>();
        MinPQ<SearchTreeNode> minPQTwin = new MinPQ<SearchTreeNode>();
        SearchTreeNode node = new SearchTreeNode(initial, 0, null);
        minPQ.insert(node);
        node = new SearchTreeNode(twin, 0, null);
        minPQTwin.insert(node);
        while (!minPQ.isEmpty() && !minPQTwin.isEmpty()) {
            SearchTreeNode now = minPQ.delMin();
            if (now.mBoard.isGoal()) {
                isSolved = true;
                moveNum = now.moves;
                ansStack = new Stack<Board>();
                while (now != now.mParent) {
                    ansStack.push(now.mBoard);
                    now = now.mParent;
                }
                ansStack.push(initial);
                break;

            }
            for (Board board : now.mBoard.neighbors()) {
                if (!board.equals(now.mParent.mBoard)) {
                    node = new SearchTreeNode(board, now.moves + 1, now);
                    minPQ.insert(node);
                }

            }

            now = minPQTwin.delMin();
            if (now.mBoard.isGoal()) {
                isSolved = false;
                moveNum = -1;
                ansStack = null;
                break;

            }
            for (Board board : now.mBoard.neighbors()) {
                if (!board.equals(now.mParent.mBoard)) {
                    node = new SearchTreeNode(board, now.moves + 1, now);
                    minPQTwin.insert(node);
                }

            }
        }
    }

    public boolean isSolvable() {
        return isSolved;
    }

    public int moves() {
        return moveNum;
    }

    public Iterable<Board> solution() {
        return ansStack;
    }

    public static void main(String[] args) {
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
    }
}