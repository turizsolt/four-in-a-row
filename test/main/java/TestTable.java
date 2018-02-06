package main.java;

import main.java.Table;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestTable {
    private int WIDTH = 7;
    private int HEIGHT = 6;
    private int MIDDLE_COLUMN = 4;
    private int LEFT_COLUMN = 0;
    private int PLAYER_ONE = 1;
    private int PLAYER_TWO = 2;

    @Test
    public void testGetTable() {
        Table table = new Table(WIDTH, HEIGHT);
        int[][] result = table.getTable();

        assertTrue(result.length == HEIGHT);
        assertTrue(result[0].length == WIDTH);
    }

    @Test
    public void testDrop() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(MIDDLE_COLUMN);
        assertEquals(PLAYER_ONE, table.getValue(0,MIDDLE_COLUMN));
    }

    @Test
    public void testDropStack() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(MIDDLE_COLUMN);
        table.drop(MIDDLE_COLUMN);
        assertEquals(PLAYER_ONE, table.getValue(0,MIDDLE_COLUMN));
        assertEquals(PLAYER_TWO, table.getValue(1,MIDDLE_COLUMN));
    }

    @Test
    public void testDropStackTooMany() {
        Table table = new Table(WIDTH, HEIGHT);
        for( int i = 0; i < HEIGHT ; i++ ) {
            assertTrue(table.drop(MIDDLE_COLUMN));
        }
        assertFalse(table.drop(MIDDLE_COLUMN));
        table.drop(LEFT_COLUMN);
        assertEquals(PLAYER_ONE, table.getValue(0,LEFT_COLUMN));
    }

    @Test
    public void testDropOutOfBounds() {
        Table table = new Table(WIDTH, HEIGHT);
        assertFalse(table.drop(-1));
        assertFalse(table.drop(WIDTH));
        for( int j = 0; j < WIDTH ; j++ ) {
            assertTrue(table.drop(j));
        }
    }

    @Test
    public void testOngoingGame() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(LEFT_COLUMN);
        assertEquals(Table.ONGOING ,table.getResult());
    }

    @Test
    public void testFourHorisontally() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN);

        table.drop(LEFT_COLUMN+1);
        table.drop(LEFT_COLUMN+1);

        table.drop(LEFT_COLUMN+2);
        table.drop(LEFT_COLUMN+2);
        assertEquals(Table.ONGOING ,table.getResult());

        table.drop(LEFT_COLUMN+3);
        assertEquals(Table.PLAYER_ONE_WON ,table.getResult());
    }

    @Test
    public void testFourVertically() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN+1);

        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN+1);

        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN+1);
        assertEquals(Table.ONGOING ,table.getResult());

        table.drop(LEFT_COLUMN);
        assertEquals(Table.PLAYER_ONE_WON ,table.getResult());
    }

    @Test
    public void testFourMainDiagonally() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN+1);
        table.drop(LEFT_COLUMN+2);
        table.drop(LEFT_COLUMN+3);

        table.drop(LEFT_COLUMN);
        table.drop(LEFT_COLUMN+2);

        table.drop(LEFT_COLUMN+1);
        table.drop(LEFT_COLUMN+1);

        table.drop(LEFT_COLUMN);
        assertEquals(Table.ONGOING ,table.getResult());

        table.drop(LEFT_COLUMN);
        assertEquals(Table.PLAYER_TWO_WON ,table.getResult());
    }

    @Test
    public void testFourSecondDiagonally() {
        Table table = new Table(WIDTH, HEIGHT);
        table.drop(LEFT_COLUMN+3);
        table.drop(LEFT_COLUMN+2);
        table.drop(LEFT_COLUMN+1);
        table.drop(LEFT_COLUMN);

        table.drop(LEFT_COLUMN+3);
        table.drop(LEFT_COLUMN+1);

        table.drop(LEFT_COLUMN+2);
        table.drop(LEFT_COLUMN+2);

        table.drop(LEFT_COLUMN+3);
        assertEquals(Table.ONGOING ,table.getResult());

        table.drop(LEFT_COLUMN+3);
        assertEquals(Table.PLAYER_TWO_WON ,table.getResult());
    }

    @Test
    public void testDraw() {
        Table table = new Table(2, 2);
        table.drop(0);
        table.drop(0);
        table.drop(1);
        assertEquals(Table.ONGOING ,table.getResult());

        table.drop(1);
        assertEquals(Table.DRAW ,table.getResult());
    }

}
