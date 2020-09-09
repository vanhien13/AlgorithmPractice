import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation. Given a composite systems comprised of randomly distributed
 * insulating and metallic materials: what fraction of the materials need to be
 * metallic so that the composite system is an electrical conductor? Given a
 * porous landscape with water on the surface (or oil below), under what
 * conditions will the water be able to drain through to the bottom (or the oil
 * to gush through to the surface)? Scientists have defined an abstract process
 * known as percolation to model such situations.
 *

 */
public class Percolation {
    private final WeightedQuickUnionUF normalQU;
    private final WeightedQuickUnionUF backwashQU;
    private final boolean[] isOpen;
    private final int tpIndex;
    private final int btmIndex;
    private final int n;
    private int siteOpen;

    public Percolation(int n){
        if (n<=0){
            throw new IllegalArgumentException("n must be greater than 0");
        }
    
    this.n=n;
    tpIndex=0;
    btmIndex=n*n+1;
    backwashQU=new WeightedQuickUnionUF(n * n+2);
    normalQU=new WeightedQuickUnionUF(n * n+1);
    isOpen=new boolean[n*n+2];
    isOpen[tpIndex]=true;
    isOpen[btmIndex]=true;
    }

    /**
   /**
     * Convert a 2D coordinate to 1D.
     *
     * @param row base-1 index of row
     * @param col base-1 index of column
     */
    private int returnIndex(int row, int col) {

    //check bounds
        if (row<1||row>n){
            throw new IllegalArgumentException("Row is out of bounds");
        }    
        if (col<1||col>n){
            throw new IllegalArgumentException("Column is out of bounds");
        }    
    
        return (row-1)*n+col;
    }

     /**
     * Open site (row, col) if it is not open already
     *
     * @param row base-1 index of row
     * @param col base-1 index of column
     */
    public void open(int row, int col) {
        int indexNow = returnIndex(row,col);
        isOpen[indexNow]=true;
        if (row==1){
            backwashQU.union(indexNow,tpIndex);
            normalQU.union(indexNow,tpIndex);
        }
        if (row==n){
            backwashQU.union(indexNow,btmIndex);
        }
        testUnion(row,col,row-1,col);
        testUnion(row,col,row+1,col);
        testUnion(row,col,row,col-1);
        testUnion(row,col,row,col+1);
    }
    private void testUnion(int rowX,int colX,int colY, int rowY){
        if(0 < rowY && rowY <=n && 0< colY && colY <=n
            && isOpen(rowY,colY)){
        backwashQU.union(returnIndex(rowX,colX),returnIndex(rowY,colY));
        normalQU.union(returnIndex(rowX,colX),returnIndex(rowY,colY));    
        }
        
    }

    public boolean isOpen(int row, int col){
        return isOpen[returnIndex(row,col)];
    }

    public boolean isFull(int row, int col){
        return normalQU.connected(tpIndex,returnIndex(row,col));
    }

    public boolean percolates(){
        return backwashQU.connected(tpIndex,btmIndex);

    }
    
    public static void main(String[] args){
        StdOut.println("Please run PercolationStats");
    }
}