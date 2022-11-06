package Protocols;


import Filtered_Info.IFilteredInfo;
import Maze_Board.Board;
import Maze_Positions.IMazePosition;
import Player_Move.IMove;

import java.util.Optional;

/**
 * Describes the promises that a player should be able to fulfill within
 * the scope of the Player-referee api.
 */
public interface IPlayerProtocol {

    /**
     * Gives the caller the name of this player.
     */
    String name();


    /**
     * Requests and receives a board which the player would play on.
     * @param rows natural number amnt of rows.
     * @param columns nat num of amnt of cols.
     * @return A board that the Referee can determine whether to play with or not -> ONLY UTILISING THE GRID
     */
    Board proposeBoard(int rows, int columns);

    /**
     * Gives this player access to an initial state and a private goal the player must visit!
     * If gs is not given, setup just tells player to go home, and goal is home.
     * returns an optional OBJECT -> essentially nothing or something
     */
    Optional<Object> setup(Optional<IFilteredInfo> gs, Coordinate goal);


    /**
     * Requests the player to make a move and then takes a Move,
     * either a skip or a shift/rotate/insert/move.
     * @param gs the gamestate that the player needs to know.
     */
    IMove takeTurn(IFilteredInfo gs);


    /**
     * Takes in whether this player won or not.
     */
    Optional<Object> won(Boolean w);

}
