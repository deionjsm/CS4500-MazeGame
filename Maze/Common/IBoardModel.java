/**
 * This interface defines what a Maze board model's functionality should be.
 * Note that this functionality is COMPLETE and not limited, only the highest
 * level of 'clearance' should be able to interact with objects of this interface.
 * TODO: it is expected that this model will extend multiple interfaces perhaps after refactoring
 * 0,0 is top left, MAX,MAX is bottom right
 * First index is ROW number aka distance from top.
 * Second index is COL number aka distance from left.
 */
public interface IBoardModel extends ILimitedBoardModel {


    /**
     * This method returns whether the requested slide of row/column was completed.
     * SIDE EFFECT: If returns true, this method will change the state of the board
     * in the expected way, knocking out a new spare tile, and having elements shifted.
     * This method will fail if used at unexpected time or in an illegal way.
     * @param index of row/col.
     * @param rORc Enum: Row or Col, representing row or column.
     * @return true if successful, false otherwise.
     */
    boolean shiftRowCol(int index, BoardOpt rORc, Direction direction);

    /**
     * This method rotates the UNPLACED spare if unplaced and returns true if successful.
     * Using degrees allows flexibility in case of tightening/loosening of rotation restrictions.
     * @param degreesCW degrees clockwise to rotate.
     *                  Up to implementation whether negatives are implemented.
     *                  Should return false for unexpected input.
     * @return true if successful, false otherwise.
     */
    boolean rotateSpare(int degreesCW);

    /**
     * This method places spare in its current state into the given space.
     * This is only done if the given space is an empty space
     * (flexible for future with more than one empty space).
     *
     * @param row the ROW value of the spot to place the spare.
     * @param col the COLUMN value of the spot to place the spare.
     * @return treu if successful, false otherwise.
     */
    boolean placeSpare(int row, int col);


    /**
     * Determines whether a position is reachable from another position.
     * Implementation tip: BFS with cycle check
     * @return true if reachable, false otherwise.
     */
    boolean isReachableFrom(int rowFrom, int colFrom, int rowTo, int colTo) throws IllegalArgumentException;

    /**
     * This returns the height of the board, aka the amount of ROWS.
     */
    int getHeight();

    /**
     * This returns the width of the board, or the amount of COLUMNS.
     */
    int getWidth();


    /**
     * USE CAREFULLY: will alter the board to undo a given move.
     */
    void undoMove(IMove move);

    /**
     * USE CAREFULLY: will alter the board to commit a given move if possible.
     */
    boolean attemptMove(IMove move);

}
