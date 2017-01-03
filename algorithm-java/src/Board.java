import java.util.*;
import edu.princeton.cs.algs4.*;

public class Board {
    private int[][] blocks;
 // construct a board from an n-by-n array of blocks
// (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.blocks = copyBlocks(blocks);
    }
 // board dimension n
    public int dimension() {
        return blocks.length;
    }
 // number of blocks out of place
    public int hamming() {
        int hamming = -1;
        for (int i = 0; i < blocks.length; i++) 
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != blocks.length * i + j + 1)
                    hamming++;
            }
        return hamming;
    }
 // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhatton = 0;
        for (int i = 0; i < blocks.length; i++) 
            for (int j = 0; j < blocks.length; j++) {
                int expected = blocks.length * i + j + 1;
                int actual = blocks[i][j];
                if (expected != actual && actual != 0) {
                    actual--;
                    int expectedI = actual / blocks.length;
                    int expectedJ = actual % blocks.length;
                    manhatton += Math.abs(expectedI - i) + Math.abs(expectedJ - j);
                }
            }
        return manhatton;
    }
 // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }
// copy the blocks    
    private int[][] copyBlocks(int[][] src) {
        int [][] copy = new int[src.length][src.length];
        for (int i = 0; i < src.length; i++) 
            for (int j = 0; j < src.length; j++) 
                copy[i][j] = src[i][j];
        return copy;
    }
 // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twin = copyBlocks(blocks);
        int i = 0;
        int j = 0;
        while (blocks[i][j] == 0 || blocks[i][j + 1] == 0) {
            j += 2;
            if (j >= blocks.length - 1) {
                i++;
                j = 0;
            }
        }
        int value = twin[i][j];
        twin[i][j] = twin[i][j + 1];
        twin[i][j + 1] = value;
        return new Board(twin);
    }
 // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }
 // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] mi = {1, 0, -1, 0};
        int[] mj = {0, 1, 0, -1};
        ArrayList<Board> neighbors = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (blocks[i][j] != 0) {
            j++;
            if (j >= blocks.length) {
                i++;
                j = 0;
            }
        }

        for (int m = 0; m < 4; m++) {
            int newI = i + mi[m];
            int newJ = j + mj[m];
            if (newI >= 0 && newJ >= 0 && 
                    newI < blocks.length && newJ < blocks.length) {
                int[][] neighbor = copyBlocks(blocks);
                neighbor[i][j] = neighbor[newI][newJ];
                neighbor[newI][newJ] = 0;
                neighbors.add(new Board(neighbor));
            }
        }
        return neighbors;
    }
 // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(blocks.length + "\n");
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) str.append(blocks[i][j] + " ");
            str.append("\n");
        }
        return str.toString();
    }
 // unit tests (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial);
        System.out.println(initial.manhattan());
        System.out.println(initial.hamming());
        System.out.println(initial.twin());
//        for (Board neighbor: initial.neighbors())
//            System.out.println(neighbor);
    }
}

