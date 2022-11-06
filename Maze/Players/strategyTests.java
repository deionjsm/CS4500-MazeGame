package Players;

import Common.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class strategyTests {


    @Test
    public void testRiemannStrategy() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        IBoardModel board = new Board(); //board full of crosses
        ITile spare = new MazeTileSimple(ConnectorShape.Cross);
        IMazePosition myPos = new MazePositionSimple(0,0);
        IMazePosition myGoal = new MazePositionSimple(6,6);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new RiemannStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip());
        assertFalse(moveReturned.doShiftAndInsert());
        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), myGoal.getXCoordinate());
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), myGoal.getYCoordinate());


    }


    @Test
    public void testRiemannStrategyNOREACHGOAL() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        IBoardModel board = new Board(); //board full of crosses
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(3,BoardOpt.Row,Direction.Left);
        //empty row cannot reach past
        ITile spare = new MazeTileSimple(ConnectorShape.Empty);
        //no tile placement will work
        IMazePosition myPos = new MazePositionSimple(1,2);
        IMazePosition myGoal = new MazePositionSimple(6,6);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new RiemannStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip());
        assertTrue(moveReturned.doShiftAndInsert());
        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), 0);
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), 0);


    }

    @Test
    public void testRiemannStrategyNOREACHANY() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        IBoardModel board = new Board(); //board full of crosses
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);
        board.shiftRowCol(0,BoardOpt.Row,Direction.Left);

        board.shiftRowCol(0,BoardOpt.Col,Direction.Down);

        //empty row cannot reach past
        ITile spare = new MazeTileSimple(ConnectorShape.Empty);
        //no tile placement will work
        IMazePosition myPos = new MazePositionSimple(0,0);
        IMazePosition myGoal = new MazePositionSimple(6,6);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new RiemannStrategy(info);
        IMove moveReturned = strat.getDecision();

        //check that move is as expected
        assertFalse(moveReturned.getSkip());
        assertTrue(moveReturned.doShiftAndInsert());
        //has shift if looking thru subgoals
        //CAN ONLY REACH SELF
        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), 0);
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), 0);

    }


    @Test
    public void testRiemannStrategy1Shift() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        Board board = new Board(); //board full of crosses
        board.shiftRowCol(6,BoardOpt.Row,Direction.Left);
        //starting with 0,0 being EMPTY need a single left shift and insert to reach
        //goal - THIS TEST ALSO SHOWS PRIORITY OF GOAL OVER SECONDARY

        //empty row cannot reach past
        ITile spare = new MazeTileSimple(ConnectorShape.Cross);
        //no tile placement will work
        IMazePosition myPos = new MazePositionSimple(6,6);
        IMazePosition myGoal = new MazePositionSimple(5,5);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new RiemannStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip()); //NOTE - NOT A SKIP DESPITE MOVE TO OWN SPOT!
        assertTrue(moveReturned.doShiftAndInsert());

        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), 5);
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), 5);
        assertEquals(moveReturned.getRotation(), 0);
        assertEquals(moveReturned.getShiftDirection(), Direction.Left);
        assertEquals(moveReturned.getShiftIndex(),6);

    }




    //EUCLIDIEAN TIME

    @Test
    public void testEUCLIDStrategy() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        IBoardModel board = new Board(); //board full of crosses
        ITile spare = new MazeTileSimple(ConnectorShape.Cross);
        IMazePosition myPos = new MazePositionSimple(0,0);
        IMazePosition myGoal = new MazePositionSimple(6,6);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new EuclidStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip());
        assertFalse(moveReturned.doShiftAndInsert());
        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), myGoal.getXCoordinate());
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), myGoal.getYCoordinate());


    }


    @Test
    public void testEuclidStrategyNOREACHGOAL() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        Board board = new Board(); //board full of crosses

        List<List<IMazePosition>> boardstate = board.getState();
        boardstate.get(3).set(0, new MazePositionSimple(3,0)); //empty
        boardstate.get(3).set(1, new MazePositionSimple(3,1)); //empty
        boardstate.get(3).set(2, new MazePositionSimple(3,2)); //empty
        boardstate.get(3).set(3, new MazePositionSimple(3,3)); //empty
        boardstate.get(3).set(4, new MazePositionSimple(3,4)); //empty
        boardstate.get(3).set(5, new MazePositionSimple(3,5)); //empty
        boardstate.get(3).set(6, new MazePositionSimple(3,6)); //empty
        boardstate.get(4).set(0, new MazePositionSimple(4,0)); //empty
        boardstate.get(4).set(1, new MazePositionSimple(4,1)); //empty
        boardstate.get(4).set(2, new MazePositionSimple(4,2)); //empty
        boardstate.get(4).set(3, new MazePositionSimple(4,3)); //empty
        boardstate.get(4).set(4, new MazePositionSimple(4,4)); //empty
        boardstate.get(4).set(5, new MazePositionSimple(4,5)); //empty
        boardstate.get(4).set(6, new MazePositionSimple(4,6)); //empty
        //empty rows cannot reach past
        ITile spare = new MazeTileSimple(ConnectorShape.Cross);
        //no tile placement will work
        IMazePosition myPos = new MazePositionSimple(1,2);
        IMazePosition myGoal = new MazePositionSimple(6,6);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new EuclidStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip());
        assertTrue(moveReturned.doShiftAndInsert());
        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), 3);
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), 6);
        assertEquals(moveReturned.getShiftIndex(), 3);
        assertEquals(moveReturned.getShiftDirection(), Direction.Left);

    }



    @Test
    public void testEuclidStrategy1Shift() {
        //strategy will return a move, check that gives expected move
        //check gives skip if runs out of options

        Board board = new Board(); //board full of crosses
        board.shiftRowCol(6,BoardOpt.Row,Direction.Left);
        //starting with 0,0 being EMPTY need a single left shift and insert to reach
        //goal - THIS TEST ALSO SHOWS PRIORITY OF GOAL OVER SECONDARY

        //empty row cannot reach past
        ITile spare = new MazeTileSimple(ConnectorShape.Cross);
        //no tile placement will work
        IMazePosition myPos = new MazePositionSimple(6,6);
        IMazePosition myGoal = new MazePositionSimple(5,5);
        IMazePosition myHome = new MazePositionSimple(0,0);
        IFilteredInfo info = new SimpleFilteredInfo(board, spare, myPos, myGoal, myHome);
        IStrategy strat = new EuclidStrategy(info);
        IMove moveReturned = strat.getDecision();
        //check that move is as expected
        assertFalse(moveReturned.getSkip()); //NOTE - NOT A SKIP DESPITE MOVE TO OWN SPOT!
        assertTrue(moveReturned.doShiftAndInsert());

        assertEquals(moveReturned.getMoveAvatarPos().getXCoordinate(), 5);
        assertEquals(moveReturned.getMoveAvatarPos().getYCoordinate(), 5);
        assertEquals(moveReturned.getRotation(), 0);
        assertEquals(moveReturned.getShiftDirection(), Direction.Left);
        assertEquals(moveReturned.getShiftIndex(),6);

    }



}
