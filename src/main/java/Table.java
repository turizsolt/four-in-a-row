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

    public Table(int _width, int _height) {
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
        if( j < 0 ) return false;
        if( j > width-1 ) return false;

        boolean dropped = false;
        for( int i = 0 ; i < height && !dropped ; i++ ) {
            if(table[i][j] == 0) {
                table[i][j] = next;
                dropped = true;
            }
        }

        if(dropped) {
            next = (next == 1) ? 2 : 1;
            spaceLeft--;
        }

        return dropped;
    }

    public int getResult() {
        if(spaceLeft == 0) return DRAW;
        int ret = 0;
        for(int i = 0; i < height ; i++ ) {
            ret = isFour(i, 0, 0, 1);
            if (ret > 0) return ret;
        }
        for(int j = 0; j < width ; j++ ) {
            ret = isFour(0, j, 1, 0);
            if (ret > 0) return ret;
        }
        for(int i = 0; i < height ; i++ ) {
            ret = isFour(i, 0,-1,1);
            if (ret > 0) return ret;
            ret = isFour(i, 0,1,1);
            if (ret > 0) return ret;
        }
        for(int j = 0; j < width ; j++ ) {
            ret = isFour(height-1, j, -1, 1);
            if (ret > 0) return ret;
            ret = isFour(0, j,1,1);
            if (ret > 0) return ret;
        }
        return ONGOING;
    }

    private int isFour(int i, int j, int ie, int je) {
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
}