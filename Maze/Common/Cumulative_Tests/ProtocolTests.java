package Cumulative_Tests;


import Filtered_Info.IFilteredInfo;
import Filtered_Info.SimpleFilteredInfo;
import GameState.State;
import Maze_Board.Board;
import Maze_Board.IBoardModel;
import Players.IRefPlayer;
import Players.SimpleRefPlayer;
import Protocols.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class ProtocolTests {

    @Test
    public void testPlayerAPI() {

        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(0,0,
                2,0,
                0,0,
                7,7);
        pl.add(player);
        State s = new State(b,7,7,pl,0);

        List<IPlayerProtocol> pp = new ArrayList<>();
        IPlayerProtocol playerPro = new Player("Jeff");
        pp.add(playerPro);


        IFilteredInfo playerInfo = new SimpleFilteredInfo(b, b.getSpare(), b.getPositions().get(0).get(0),
                b.getPositions().get(2).get(2), b.getPositions().get(0).get(0));

        assertEquals(playerPro.name(), "Jeff");

        assertEquals(playerPro.won(true), Optional.empty());
        assertEquals(playerPro.won(false), Optional.empty());

        assertEquals(playerPro.setup(Optional.empty(), new Coordinate(playerInfo.getMyGoal().getXCoordinate(),
                playerInfo.getMyGoal().getYCoordinate())), Optional.empty());

    }

    @Test
    public void testRunGameFromState() {

        IBoardModel b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(1,1,
                2,2,
                1,1,
                7,7, "Dave");
        pl.add(player);
        State s = new State(b,7,7,pl,0);

        List<IPlayerProtocol> pp = new ArrayList<>();
        IPlayerProtocol playerPro = new Player("Dave");
        pp.add(playerPro);
        IReferee ref = new RefProtocol(pp, b);
        Map<Boolean, List<IPlayerProtocol>> expected = new HashMap<>();
        expected.put(true, pp);
        expected.put(false, new ArrayList<>());
        assertEquals(ref.runGameFromState(s), expected);

    }

    @Test
    public void testRunGameFromState2() {

        IBoardModel b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(1,1,
                2,2,
                1,1,
                7,7, "Fred");
        pl.add(player);
        State s = new State(b,7,7,pl,0);

        List<IPlayerProtocol> pp = new ArrayList<>();
        IPlayerProtocol playerPro = new Player("Dave");
        pp.add(playerPro);
        IReferee ref = new RefProtocol(pp, b);
        Map<Boolean, List<IPlayerProtocol>> expected = new HashMap<>();
        expected.put(true, new ArrayList<>());
        expected.put(false, new ArrayList<>());
        assertEquals(ref.runGameFromState(s), expected);

    }


    @Test
    public void testRunGameFromState3() {

        IBoardModel b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(1,1,
                2,2,
                1,1,
                7,7, "Dave");
        IRefPlayer player2 = new SimpleRefPlayer(1,1,
                2,2,
                1,1,
                7,7, "Fred");
        pl.add(player);
        pl.add(player2);
        State s = new State(b,7,7,pl,0);

        List<IPlayerProtocol> pp = new ArrayList<>();
        IPlayerProtocol playerPro = new Player("Dave");
        IPlayerProtocol playerPro2 = new Player("Fred");
        pp.add(playerPro);
        pp.add(playerPro2);
        IReferee ref = new RefProtocol(pp, b);
        Map<Boolean, List<IPlayerProtocol>> expected = new HashMap<>();
        List <IPlayerProtocol> t = new ArrayList<>();
        t.add(playerPro);
        expected.put(true, t);
        expected.put(false, new ArrayList<>());
        assertEquals(ref.runGameFromState(s), expected);

    }
}
