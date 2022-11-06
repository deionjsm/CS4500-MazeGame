package Players;

import Enum_Constants.Direction;

import java.awt.*;

/**
 * This interface describes the functionality of a referee's concept of a player.
 * This interface describes what a referee should be able to do with a player.
 * Conceptually, a player has a HOME, a GOAL, an AVATAR, and CURRENT POSITION.
 */

public interface IRefPlayer {


    /**
     * This method gives the row that this player is currently on.
     * @return the int value of a row, MAX being right, 0 being left.
     */
    int getCurrentRow();

    /**
     * This method gives the col that this player is currently on.
     * @return the int value of column - MAX being bottom, 0 being top.
     */
    int getCurrentColumn();


    /**
     * Returns whether this players position is on their goal.
     * @return true if on this player's goal. false otherwise.
     */
    boolean isOnGoal();


    /**
     * Shifts the player in a direction. If the player is to fall off the edge,
     * this method will place them on the "spare" position at the beginning of the shift.
     * @param direction the direction to shift the player.
     * @return true if completed. false otherwise.
     */
    boolean shiftPlayer(Direction direction);


    /**
     * Returns this players color.
     * @return a color that represents this players avatar
     */
    Color getPlayerColor();

    /**
     * Gets the row value of the goal.
     * NOTE GOAL IS IMMOVABLE -> SET TO AN INDEX
     * @return int index of goal
     */
    int getGoalRow();

    /**
     * Gets the col value of the goal.
     * NOTE GOAL IS IMMOVABLE -> SET TO AN INDEX
     * @return int index of goal
     */
    int getGoalCol();

    /**
     * Gets the row value of the home.
     * NOTE HOME IS IMMOVABLE -> SET TO AN INDEX
     * @return int index of home
     */
    int getHomeRow();

    /**
     * Gets the col value of the home.
     * NOTE HOME IS IMMOVABLE -> SET TO AN INDEX
     * @return int index of home
     */
    int getHomeCol();

    /**
     * Moves the player to given position.
     * @return true if possible move.
     */
    boolean movePlayerTo(int row, int col);

    /**
     * Returns whether this player has reached their goal-tile at some point in their lifetime.
     */
    boolean getHasReachedGoal();

    /**
     *  Checks whether player has reached goaltile AND is home, essentially winning.
     */
    boolean hasWon();

    /**
     * Returns an identifier -> name in this circumstance.
     * @return
     */
    String getIdentifier();

    void setGoalToHome();

    IRefPlayer getCopy();

}
