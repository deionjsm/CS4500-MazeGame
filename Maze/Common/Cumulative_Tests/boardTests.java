package Cumulative_Tests;

import Maze_Board.Board;
import Maze_Positions.IMazePosition;
import Enum_Constants.BoardOpt;
import Enum_Constants.Direction;
import Maze_Board.Board;
import Players.IRefPlayer;
import Players.SimpleRefPlayer;
import GameState.State;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests boards public methods and the side effects they create.
 */
public class boardTests {


    

    //Set up board using ctor -> 2 by 2, one empty slot
    //3 by 3 one empty slot
    //give a posn w a tile as spare

    @Test
    public void testRowShift() {
        Board b = new Board();

        assertTrue(b.shiftRowCol(0, BoardOpt.Row, Direction.Right));
        assertTrue(b.shiftRowCol(2, BoardOpt.Row, Direction.Left));
        List< List <IMazePosition>> ls =  b.getState();
        //new empty at LEFT OF ROW 0
        /*
        System.out.println(ls.get(2).get(6).getTile().getConnector());
        System.out.println(ls.get(2).get(5).getTile().getConnector());
        System.out.println(ls.get(2).get(4).getTile().getConnector());
        System.out.println(ls.get(2).get(3).getTile().getConnector());
        System.out.println(ls.get(0).get(2).getTile().getConnector());
        System.out.println(ls.get(0).get(1).getTile().getConnector());
        System.out.println(ls.get(2).get(0).getTile().getConnector());
        */
        assertTrue(ls.get(0).get(0).getTile().isEmpty());
        assertFalse(ls.get(0).get(1).getTile().isEmpty());
        assertFalse(ls.get(0).get(2).getTile().isEmpty());
        assertFalse(ls.get(0).get(3).getTile().isEmpty());
        assertFalse(ls.get(0).get(4).getTile().isEmpty());
        assertFalse(ls.get(0).get(5).getTile().isEmpty());
        assertFalse(ls.get(0).get(6).getTile().isEmpty());

        assertTrue(ls.get(2).get(6).getTile().isEmpty());
        assertFalse(ls.get(2).get(5).getTile().isEmpty());
        assertFalse(ls.get(2).get(4).getTile().isEmpty());
        assertFalse(ls.get(2).get(3).getTile().isEmpty());
        assertFalse(ls.get(2).get(2).getTile().isEmpty());
        assertFalse(ls.get(2).get(1).getTile().isEmpty());
        assertFalse(ls.get(2).get(0).getTile().isEmpty());
    }

    @Test
    public void testColShift() {
        Board b = new Board();

        assertTrue(b.shiftRowCol(0, BoardOpt.Col, Direction.Up));
        assertTrue(b.shiftRowCol(2, BoardOpt.Col, Direction.Down));
        List< List <IMazePosition>> ls =  b.getState();
        assertTrue(ls.get(6).get(0).getTile().isEmpty());
        assertTrue(ls.get(0).get(2).getTile().isEmpty());
    }

    @Test
    public void testInvalidShifts() {
        Board b = new Board();

        assertFalse(b.shiftRowCol(-1, BoardOpt.Col, Direction.Up));
        assertFalse(b.shiftRowCol(7, BoardOpt.Col, Direction.Down));
        assertFalse(b.shiftRowCol(-1, BoardOpt.Row, Direction.Left));
        assertFalse(b.shiftRowCol(7, BoardOpt.Row, Direction.Right));


    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidShiftDirections1() {
        Board b = new Board();
        b.shiftRowCol(1,BoardOpt.Row, Direction.Up);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidShiftDirections2() {
        Board b = new Board();
        b.shiftRowCol(1,BoardOpt.Row, Direction.Down);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidShiftDirections3() {
        Board b = new Board();
        b.shiftRowCol(1,BoardOpt.Col, Direction.Left);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidShiftDirections4() {
        Board b = new Board();
        b.shiftRowCol(1, BoardOpt.Col, Direction.Right);
    }


    @Test
    public void testPlaceSpare() {
        Board b = new Board();
        assertTrue(b.shiftRowCol(0, BoardOpt.Row, Direction.Right));
        List< List <IMazePosition>> ls =  b.getState();
        assertTrue(ls.get(0).get(0).getTile().isEmpty());
        //spare by default is cross
        assertTrue(b.placeSpare(0,0));
        ls = b.getState();
        assertFalse(ls.get(0).get(0).getTile().isEmpty());
    }

    @Test
    public void testInvalidPlaceSpare() {
        Board b = new Board();
        assertTrue(b.shiftRowCol(0, BoardOpt.Row, Direction.Right));
        List< List <IMazePosition>> ls =  b.getState();
        assertTrue(ls.get(0).get(0).getTile().isEmpty());
        //spare by default is cross
        assertFalse(b.placeSpare(1,1));
        assertFalse(b.placeSpare(-1,-1));
        assertFalse(b.placeSpare(7,7));

    }



    @Test
    public void testReachable() {
        Board b = new Board();
        //all reachable
        assertTrue(b.isReachableFrom(0,0,0,1));
        assertTrue(b.isReachableFrom(0,0,0,0));
        assertTrue(b.isReachableFrom(0,0,1,1));
        assertTrue(b.isReachableFrom(0,0,5,5));
        assertTrue(b.shiftRowCol(0, BoardOpt.Row, Direction.Right));
        //NOW 0,0 IS UNREACHABLE TO ALL - EMPTY
        assertFalse(b.isReachableFrom(0,0,1,1));
        assertFalse(b.isReachableFrom(0,0,5,5));

        //not a loop !!! yusss :))))
        assertFalse(b.isReachableFrom(5,5,0,0));
        assertFalse(b.isReachableFrom(6,6,0,0));
        assertFalse(b.isReachableFrom(1,1,0,0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable1() {
        Board b = new Board();
        b.isReachableFrom(-1,0,0,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable2() {
        Board b = new Board();
        b.isReachableFrom(1,-10,0,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable3() {
        Board b = new Board();
        b.isReachableFrom(1,0,-2,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable4() {
        Board b = new Board();
        b.isReachableFrom(1,0,0,-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable5() {
        Board b = new Board();
        b.isReachableFrom(7,0,7,1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidReachable6() {
        Board b = new Board();
        b.isReachableFrom(1,7,0,7);
    }


}
