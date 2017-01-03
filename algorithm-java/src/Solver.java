import edu.princeton.cs.algs4.*;

public class Solver {
    private MinPQ<Node> searchPQ;
    private MinPQ<Node> twinPQ;
    private boolean isSolvable;
    private int moves;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private Node prev;
        private int moves;
        private int priority;
        
        private Node(Board board, Node prev, int moves) {
            this.board = board;
            this.prev = prev;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }
        
        @Override
        public int compareTo(Node that) {
            return this.priority - that.priority;
        }    
    }
 // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new java.lang.NullPointerException();
        searchPQ = new MinPQ<>();
        twinPQ = new MinPQ<>();
        searchPQ.insert(new Node(initial, null, 0));
        twinPQ.insert(new Node(initial.twin(), null, 0));
        
        while (!searchPQ.min().board.isGoal() && !twinPQ.min().board.isGoal()) {
            Node searchMin = searchPQ.delMin();
//            System.out.println(searchMin.board);
            Node twinMin = twinPQ.delMin();
            Iterable<Board> searchNeighbors = searchMin.board.neighbors();
            for (Board neighbor: searchNeighbors) {
                
                if (searchMin.prev == null || !neighbor.equals(searchMin.prev.board))
                    searchPQ.insert(new Node(neighbor, searchMin, searchMin.moves + 1));
            }
                
            Iterable<Board> twinNeighbors = twinMin.board.neighbors();
            for (Board twinNeighbor: twinNeighbors) {
                if (twinMin.prev == null || !twinNeighbor.equals(twinMin.prev.board)) 
                    twinPQ.insert(new Node(twinNeighbor, twinMin, twinMin.moves + 1));    
            }
        }
        
        if (searchPQ.min().board.isGoal()) {
            isSolvable = true;
            moves = searchPQ.min().moves;
        }
        if (twinPQ.min().board.isGoal()) {
            isSolvable = false;
            moves = -1;
        }
    }
 // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }
 // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }
 // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable) return null;
        Stack<Board> solution = new Stack<> ();
        Node step = searchPQ.min();
        while (step != null) {
            solution.push(step.board);
            step = step.prev;
        }
        return solution;
    }
 // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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