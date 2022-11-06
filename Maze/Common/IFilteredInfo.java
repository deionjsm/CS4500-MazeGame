/**
 * This interface describes the filtered information a single player has.
 * This includes:
 * a filtered down board that is viewable,
 * A COPY OF the current spare tile,
 * this players position, goal, and home tile
 * other players positions
 * GEMS POSITIONS
 */
public interface IFilteredInfo {
    /**
     * Gives a board model.
     * @return a board model with all functionality so that the user can test different board options.
     */
    IBoardModel getCurrentBoard();

    /**
     * Gets current spare tile for the boards state.
     * @return ITILE representing wat the actual tile is.
     */
    ITile getCurrentSpareTile();

    /**
     * gets a maze position with the proper row/col index for this player.
     * @return
     */
    IMazePosition getMyPosition();

    /**
     * gets a maze position with the proper row/col index for this player's goal.
     * @return
     */
    IMazePosition getMyGoal();


    /**
     * gets a maze position with the proper row/col index for this player's home.
     * @return
     */
    IMazePosition getMyHome();




    //TODO ADD CAPABILITY TO CHECK OTHER PLAYERS POSITIONS, GEMS POSITIONS

}
