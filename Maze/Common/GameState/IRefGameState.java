package GameState;

import Enum_Constants.BoardOpt;
import Enum_Constants.Direction;
import Filtered_Info.IFilteredInfo;
import Maze_Board.IBoardModel;
import Maze_Tiles.ITile;
import Players.IRefPlayer;

import java.util.List;

/**
 * An interface that describes the functionality required by a referee's game state.
 * This interface promises access to:
 * Current state of the board.
 * The spare tile.
 * Player assigned homes, goals, and current positions.
 * Note this interface does not express the need for the referee to adequately manage the ACTIVE player.
 */

public interface IRefGameState {

    /**
     * This method transforms the current spare tile by a number of given degrees.
     * @param degreesCW the degrees clockwise as an integer value.
     * @return True if completed action, false otherwise.
     */
    boolean rotateSpareTile(int degreesCW);

    /**
     * NOTE: moves player from created spare tile to just inserted one if 'falls off' the board during shift.
     * This method shifts the board in the designated fashion, and proceeds to place the current spare tile in
     * the would-be empty slot, and designating the popped out spare to be the new spare.
     * @param indexOfRowOrCol the index of the row/column to be moved - 0,0 top left, x,x bottom right.
     * @param rORc row or column.
     * @param direction the direction of the shift.
     * @return True if completed action, false if otherwise.
     */
    boolean shiftAndInsert(int indexOfRowOrCol, BoardOpt rORc, Direction direction);


    /**
     * Determines whether the active player can reach a given tile.
     * @return True if this point is reachable by active player's position.
     */
    boolean isReachableByActivePlayer(int rowTo, int colTo);


    /**
     * Determines whether active player is on their goal.
     * @return true if the active player is on their goal, false otherwise.
     */
    boolean isActiveOnGoal();


    /**
     * Will remove this player from the game in the eyes of the referee.
     * @return True if successfully completed. False otherwise.
     */
    boolean kickActivePlayer();

    /**
     * Will move player to given position or return false if cannot.
     * @return True if side effect of player movement completed.
     */
    boolean moveActiveTo(int rowNumber, int colNumber);

    /**
     * Determines whether any player has won by reaching their goalstate and back home.
     * @return true if yes, false otherwise.
     */
    boolean anyPlayersWon();

    /**
     * Returns winners per "a player reaches its home after visiting its designated goal tile or,
     * winners are all those players that are on their way back home and share the same minimal
     * Euclidean distance to their home; or if no players are on their way back from their assigned goals,
     * winners are all those players that share the same minimal Euclidean distance to their respective goal tile."
     * @return List of winners so far.
     */
    List<IRefPlayer> allWinners();

    /**
     * Gets a deep copy of the current board.
     * @return deep copy of board.
     */
    IBoardModel getBoardCopy();

    /**
     * Returns deep copy of spare tile.
     * @return
     */
    ITile getSpareCopy();

    IFilteredInfo getFilteredInfo();

    String getNameOfActive();

    /**
     * Progresses turn for the state.
     */
    void progressTurn();

    void setGoalToHome();

    //deep copy of this gamestate
    IRefGameState getCopy();


    List <IRefPlayer> getPlayers();
}
