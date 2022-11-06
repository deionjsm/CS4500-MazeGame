package Protocols;

import Filtered_Info.IFilteredInfo;
import Maze_Board.Board;
import Player_Move.IMove;
import Player_Strategies.EuclidStrategy;
import Player_Strategies.IStrategy;
import Player_Strategies.RiemannStrategy;

import java.util.Optional;

/**
 * This class implements a Player's end of the referee-player protocol
 */
public class Player implements IPlayerProtocol{


    String Name;
    private boolean won = false;

    public Player(String name) {
        this.Name = name;
    }

    // the player is asked to propose a board that has a minimum of rows and columns specified by the args
    @Override
    public Board proposeBoard(int rows, int columns) {
        if (rows < 0 || columns < 0) { //not nats!
            throw new IllegalArgumentException();
        }
        //MAY add some sort of board deliberation in here -> determine board that puts me in a good position?
        return new Board(rows, columns);
    }

    @Override
    public String name() {
        return Name;
    }

    @Override
    public Optional<Object> setup(Optional<IFilteredInfo> gs, Coordinate goal) {
        //if gs is NONE setup is used to tell the player to go home
        return Optional.empty();
    }

    // the player requests a move once queried by the referee
    @Override
    public IMove takeTurn(IFilteredInfo gs) {

        IStrategy euclidStrategy = new EuclidStrategy();
        IStrategy riemannStrategy = new RiemannStrategy();

        return euclidStrategy.getDecision(gs);
    }


    public Optional<Object> won(Boolean w) {
        //I AM NOW INFORMED
        won = w;
        if (w) {
            //i won !!
            return Optional.empty();
        }
        //I lost :(
        return Optional.empty();
    }
}
