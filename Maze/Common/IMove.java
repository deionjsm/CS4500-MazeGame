/**
 * This interface contains the necessary data for a players move.
 * This move does not make any assumptions regarding whether a move is legal or not, simply that
 * it is populated with well-formed values NOT VALIDITY.
 * NOTE A MOVE TO A PLAYERS CURRENT SPOT IS NOT AND CANNOT BE CHECKED IN MOVE.
 */
public interface IMove {

    /**
     * @return TRUE if this move should be skipped! False if this move should be executed.
     */
    boolean getSkip();

    /**
     * @return TRUE if move has a shift, rotate, and insert! False if this shift should not be executed.
     */
    boolean doShiftAndInsert();

    /**
     * @return
     * @throws IllegalStateException if state has not been updated since creation and set.
     */
    int getShiftIndex()  throws IllegalStateException;

    /**
     * @return
     * @throws IllegalStateException if state has not been updated since creation and set.
     */
    Direction getShiftDirection() throws IllegalStateException;

    /**
     * @return
     * @throws IllegalStateException if state has not been updated since creation and set.
     */
    int getRotation() throws IllegalStateException;

    /**
     * @return Mazeposition with valid row col data such that
     * @throws IllegalStateException if state has not been updated since creation and set.
     */
    IMazePosition getMoveAvatarPos() throws IllegalStateException;



}
