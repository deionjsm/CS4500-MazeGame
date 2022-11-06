package Cumulative_Tests;

import Enum_Constants.ConnectorShape;
import GameState.IRefGameState;
import GameState.State;
import Maze_Board.Board;
import Maze_Board.IBoardModel;
import Maze_Positions.IMazePosition;
import Maze_Positions.MazePositionSimple;
import Maze_Tiles.ITile;
import Maze_Tiles.MazeTileSimple;
import Players.IRefPlayer;
import Players.SimpleRefPlayer;
import Protocols.Observer;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ObserverIntegrationTests {

    public static void main(String[] args) throws IOException {

        Board b = new Board();
        List<IRefPlayer> pl = new ArrayList<>();
        IRefPlayer player = new SimpleRefPlayer(0,0,
                0,0,
                0,0,
                7,7,
                "Dave");
        pl.add(player);
        State s = new State(b,7,7,pl,0);
        Observer obs = new Observer(s);

        List<List <IMazePosition>> positions = new ArrayList<>();
        // initialize list of maze positions with a tile
        for (int row = 0; row < 7; row++) {
            ArrayList <IMazePosition> temp = new ArrayList<>();
            for (int col1 = 0; col1 < 7; col1++) {

                IMazePosition tempPosition = new MazePositionSimple(row,col1,
                        new MazeTileSimple(ConnectorShape.UpRight));

                temp.add(tempPosition);
            }

            positions.add(temp);
        }

        Queue<ITile> std = new ArrayDeque<>();
        std.add(new MazeTileSimple(ConnectorShape.UpRight));

        IBoardModel testableBoard = new Board(7, 7, positions, std, true);
        State s2 = new State(testableBoard,7,7,pl,0);
        obs.receiveState(s2);
        //TWO SIZE STATE
        obs.startGUI();


    }
}
