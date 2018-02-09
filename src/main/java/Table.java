package main.java;

public class Table {

    public static final int ONGOING = 0;
    public static final int PLAYER_ONE_WON = 1;
    public static final int PLAYER_TWO_WON = 2;
    public static final int DRAW = 3;

    private int[][] table;
    private int next;
    private int width;
    private int height;
    private int spaceLeft;
    private DropData lastDrop;

    Table(int _width, int _height) {
        width = _width;
        height = _height;
        table = new int[height][width];
        for( int i = 0 ; i < height ; i++ ) {
            for( int j = 0 ; j < width ; j++ ) {
                table[i][j] = 0;
            }
        }

        next = 1;
        spaceLeft = width*height;
    }

    public int[][] getTable() {
        return table;
    }

    public void printTable() {
        for( int i = height-1 ; i > -1 ; i-- ) {
            for( int j = 0 ; j < width ; j++ ) {
                System.out.print(table[i][j]);
            }
            System.out.println();
        }
    }

    public int getValue(int i, int j) {
        return table[i][j];
    }

    public boolean drop(int j) {
        if( !isInBound(j) ) return false;

        boolean dropped = false;
        int i;
        for( i = 0 ; i < height ; i++ ) {
            if(isEmptyCell(i, j)) {
                return dropToCell(i, j);
            }
        }

        lastDrop = null;
        return false;
    }

    private boolean dropToCell(int i, int j) {
        table[i][j] = next;
        lastDrop = new DropData(i,j,next);
        swapNext();
        spaceLeft--;
        return true;
    }

    private void swapNext() {
        next = (next == 1) ? 2 : 1;
    }

    private boolean isEmptyCell(int i, int j) {
        return table[i][j] == 0;
    }

    public int getResult() {
        if(spaceLeft == 0) return DRAW;

        int ret = 0;
        for(int i = 0; ret == 0 && i < height ; i++ ) {
            ret = isFour(ret, i, 0, 0, 1);
            ret = isFour(ret, i, 0,1,1);
            ret = isFour(ret, i, 0,-1,1);
        }
        for(int j = 0; ret == 0 && j < width ; j++ ) {
            ret = isFour(ret, 0, j, 1, 0);
            ret = isFour(ret,0, j,1,1);
            ret = isFour(ret,height-1, j, -1, 1);
        }
        return ret;
    }

    public DropData getLastDrop() {
        return lastDrop;
    }

    private int isFour(int ret, int i, int j, int ie, int je) {
        if(ret > 0) return ret;

        int last = table[i][j];
        int count = 1;
        i += ie;
        j += je;
        while(isInBound(i, j)) {
            if ( last == table[i][j] ) {
                count++;
                if( count == 4 ) {
                    return last;
                }
            } else {
                last = table[i][j];
                count = 1;
            }

            i += ie;
            j += je;
        }
        return 0;
    }

    private boolean isInBound(int i, int j) {
        return -1 < i && i < height && -1 < j && j < width;
    }

    private boolean isInBound(int j) {
        return -1 < j && j < width;
    }
}