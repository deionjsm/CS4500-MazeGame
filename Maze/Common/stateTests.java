import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This class tests the state's public methods and the side effects they create.
 */
public class stateTests {

    @Test
    public void testRotateSpare() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player);
        State s = new State(b,7,7,pl,0);

        assertTrue(s.rotateSpareTile(90));
        assertTrue(b.getSpare().getRotationCW() == 90  );
        assertTrue(s.rotateSpareTile(180));
        assertTrue(b.getSpare().getRotationCW() == 270  );
        assertTrue(s.rotateSpareTile(270));
        assertTrue(b.getSpare().getRotationCW() == 180  );
        assertTrue(s.rotateSpareTile(0));
        assertTrue(b.getSpare().getRotationCW() == 180  );

    }

    @Test
    public void testFailRotateSpare() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player);
        State s = new State(b,7,7,pl,0);

        assertFalse(s.rotateSpareTile(89));
        assertTrue(b.getSpare().getRotationCW() == 0  );
        assertFalse(s.rotateSpareTile(181));
        assertTrue(b.getSpare().getRotationCW() == 0  );
        assertFalse(s.rotateSpareTile(355));
        assertFalse(s.rotateSpareTile(-1));
        assertTrue(b.getSpare().getRotationCW() == 0  );
    }


    @Test
    public void testShiftAndInsert() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player1);
        State s = new State(b,7,7,pl,0);

        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Right));
        List<List<IMazePosition> > hlp = b.getState();
        //check that spare was placed
        assertFalse(hlp.get(0).get(6).getTile().isEmpty());
        assertFalse(hlp.get(0).get(0).getTile().isEmpty());
        //check player semantics
        assertEquals(player1.getCurrentColumn(),1);
        assertEquals(player1.getCurrentRow(),0);
        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Left));
        assertEquals(0,player1.getCurrentColumn());
        assertEquals(0,player1.getCurrentRow());
        //SWITCH LEFT
        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Left));
        assertEquals(player1.getCurrentColumn(),6);
        assertEquals(player1.getCurrentRow(),0);
        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Left));
        assertEquals(player1.getCurrentColumn(),5);
        assertEquals(player1.getCurrentRow(),0);
        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Right));
        assertEquals(player1.getCurrentColumn(),6);
        assertEquals(player1.getCurrentRow(),0);
        //SWITCH SIDES RIGHT
        assertTrue(s.shiftAndInsert(0,BoardOpt.Row,Direction.Right));
        assertEquals(player1.getCurrentColumn(),0);
        assertEquals(player1.getCurrentRow(),0);

        assertTrue(s.shiftAndInsert(0,BoardOpt.Col,Direction.Down));
        assertEquals(player1.getCurrentColumn(),0);
        assertEquals(player1.getCurrentRow(),1);
        assertTrue(s.shiftAndInsert(0,BoardOpt.Col,Direction.Up));
        assertEquals(player1.getCurrentColumn(),0);
        assertEquals(player1.getCurrentRow(),0);
        //Switch UP
        assertTrue(s.shiftAndInsert(0,BoardOpt.Col,Direction.Up));
        assertEquals(player1.getCurrentColumn(),0);
        assertEquals(player1.getCurrentRow(),6);
        //switch DOWN
        assertTrue(s.shiftAndInsert(0,BoardOpt.Col,Direction.Down));
        assertEquals(player1.getCurrentColumn(),0);
        assertEquals(player1.getCurrentRow(),0);


    }

    @Test
    public void testShiftAndInsertFail() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player1);
        State s = new State(b,7,7,pl,0);

        assertFalse(s.shiftAndInsert(-1,BoardOpt.Row,Direction.Right));
        assertFalse(s.shiftAndInsert(7,BoardOpt.Row,Direction.Right));



    }

    @Test(expected = IllegalArgumentException.class)
    public void testShiftAndInsertThrow() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player1);
        State s = new State(b,7,7,pl,0);

        assertFalse(s.shiftAndInsert(0,BoardOpt.Col,Direction.Right));
        assertFalse(s.shiftAndInsert(7,BoardOpt.Col,Direction.Right));
        assertFalse(s.shiftAndInsert(0,BoardOpt.Row,Direction.Up));
        assertFalse(s.shiftAndInsert(0,BoardOpt.Row,Direction.Down));
        assertFalse(s.shiftAndInsert(0,BoardOpt.Col,Direction.Left));
        assertFalse(s.shiftAndInsert(0,BoardOpt.Col,Direction.Right));



    }

    @Test
    public void testReachableTile() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        pl.add(player1);
        State s = new State(b,7,7,pl,0);
        //ARTIFICIAL WAY TO CREATE HOLE IN BOARD -> SHIFT USING BOARD UNTIL CUT OFF
        assertTrue(s.isReachableByActivePlayer(0,0));
        assertTrue(s.isReachableByActivePlayer(1,1));
        assertTrue(s.isReachableByActivePlayer(6,0));
        assertTrue(s.isReachableByActivePlayer(0,6));
        assertTrue(s.isReachableByActivePlayer(6,6));

        //CREATE WALL AT ROW 3
        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 1/7
        List<List<IMazePosition> > hlp = b.getState();
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertFalse(hlp.get(3).get(5).getTile().isEmpty());
        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 2/7
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertTrue(hlp.get(3).get(5).getTile().isEmpty());

        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 3/7
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertTrue(hlp.get(3).get(5).getTile().isEmpty());
        assertTrue(hlp.get(3).get(4).getTile().isEmpty());
        assertFalse(hlp.get(3).get(3).getTile().isEmpty());


        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 4/7
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertTrue(hlp.get(3).get(5).getTile().isEmpty());
        assertTrue(hlp.get(3).get(4).getTile().isEmpty());
        assertTrue(hlp.get(3).get(3).getTile().isEmpty());


        b.shiftRowCol(3,BoardOpt.Row, Direction.Left);
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertTrue(hlp.get(3).get(5).getTile().isEmpty());
        assertTrue(hlp.get(3).get(4).getTile().isEmpty());
        assertTrue(hlp.get(3).get(3).getTile().isEmpty());
        assertTrue(hlp.get(3).get(2).getTile().isEmpty());
        assertFalse(hlp.get(3).get(1).getTile().isEmpty());
        assertFalse(hlp.get(3).get(0).getTile().isEmpty());


        //lost 5/7
        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 6/7
        assertTrue(hlp.get(3).get(6).getTile().isEmpty());
        assertTrue(hlp.get(3).get(5).getTile().isEmpty());
        assertTrue(hlp.get(3).get(4).getTile().isEmpty());
        assertTrue(hlp.get(3).get(3).getTile().isEmpty());
        assertTrue(hlp.get(3).get(2).getTile().isEmpty());
        assertTrue(hlp.get(3).get(1).getTile().isEmpty());
        assertFalse(hlp.get(3).get(0).getTile().isEmpty());

        b.shiftRowCol(3,BoardOpt.Row, Direction.Left); //lost 7/7
        //NOW BOTTOM HALF UNREACHABLE




        assertTrue(s.isReachableByActivePlayer(0,0));
        assertTrue(s.isReachableByActivePlayer(1,1));
        assertFalse(s.isReachableByActivePlayer(6,0));
        assertTrue(s.isReachableByActivePlayer(0,6));
        assertFalse(s.isReachableByActivePlayer(6,6));
        assertFalse(s.isReachableByActivePlayer(3,3));
        assertFalse(s.isReachableByActivePlayer(3,0));




    }


    @Test
    public void testIsOnGoal() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        IRefPlayer player2 = new SimpleRefPlayer(0,0,
                1,0,
                0,0,
                7,7);
        pl.add(player1);
        pl.add(player2);
        State s = new State(b,7,7,pl,0);

        assertTrue(s.isActiveOnGoal());
        //ON 0,0
        assertTrue(s.kickActivePlayer());
        //SWITCH TO NEXT PLAYER - rough method using kick
        assertFalse(s.isActiveOnGoal());
        //THIS PLAYER IS NOT ON GOAL OF 1,0


    }


    @Test
    public void testKickPlayer() {
        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player1 = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7);
        IRefPlayer player2 = new SimpleRefPlayer(0,0,
                1,0,
                0,0,
                7,7);
        pl.add(player1);
        pl.add(player2);
        State s = new State(b,7,7,pl,0);

        assertTrue(s.isActiveOnGoal());
        //ON 0,0
        assertTrue(s.kickActivePlayer());
        //SWITCH TO NEXT PLAYER - rough method using kick
        assertEquals(pl.size(),1);
        assertFalse(s.isActiveOnGoal());
        assertTrue(s.kickActivePlayer());
        assertEquals(pl.size(),0);
        //THIS PLAYER IS NOT ON GOAL OF 1,0


    }


}
