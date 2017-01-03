import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridLength;
    private boolean[] isOpen;
    private WeightedQuickUnionUF percolation;
    private WeightedQuickUnionUF fullness;
    private final int[] mx = {-1, 0, 1, 0};
    private final int[] my = {0, -1, 0, 1};
   
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        gridLength = n;
        isOpen = new boolean[n*n + 2];

        percolation = new WeightedQuickUnionUF(n*n + 2);
        fullness = new WeightedQuickUnionUF(n*n + 1);
    }
    
    private void checkBounds(int row, int col) {
        if (row > gridLength || row < 1) {
            throw new IndexOutOfBoundsException("row index out of bounds");
        }
        if (col > gridLength || col < 1) {
            throw new IndexOutOfBoundsException("column index out of bounds");
        }
    }
       
    private int virtualIndex(int row, int col) {
        return (row - 1) * gridLength + col;
    }
   
     // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);
        int virtualIndex = virtualIndex(row, col);
        isOpen[virtualIndex] = true;
        if (row == 1) {
            percolation.union(0, virtualIndex);
            fullness.union(0, virtualIndex);
        }
        if (row == gridLength) {
            percolation.union(gridLength*gridLength + 1, virtualIndex);
        }
        for (int m = 0; m < 4; m++) {
            int neighborRow = row + mx[m];
            int neighborCol = col + my[m];
            int neighborVitualIndex = virtualIndex(neighborRow, neighborCol);
            if (neighborRow >= 1 && neighborCol >= 1 
                    && neighborRow <= gridLength && neighborCol <= gridLength
                    && isOpen[neighborVitualIndex]) {
                percolation.union(neighborVitualIndex, virtualIndex);
                fullness.union(neighborVitualIndex, virtualIndex);
            }
        }
 
    }
   
   // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);
        return isOpen[virtualIndex(row, col)];
    }
   
    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        return fullness.connected(0, virtualIndex(row, col));
    }
   
    // does the system percolate?
    public boolean percolates() {
        return percolation.connected(0, gridLength*gridLength + 1);
    }

    // test client (optional)
    public static void main(String[] args)  {

    }
}


