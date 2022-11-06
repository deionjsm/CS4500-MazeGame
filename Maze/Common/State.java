import java.util.List;

/**
 * This concrete class implements the interface of a referee's gamestate in the classic 7 by 7 game.
 * This implementation uses a list of players and kicks by deleting the reference to player.
 */
public class State implements IRefGameState {

    private final IBoardModel mazeboard;

    //maze height is one more than the max index of a row -> furthest down
    private final int mazeHeight;

    //Maze width is one more than the max index of a column -> furthest right
    private final int mazeWidth;
    //TODO SORT BY AGE
    private final List<IRefPlayer> playerList;

    //determines active player
    private int indexOfActive;


    /**
     * Simple constructor setting board, given all needed and useful information
     */
    public State(IBoardModel mazeboard,
                 int mazeHeight, int mazeWidth,
                 List<IRefPlayer> playerList,
                 int indexOfActive) {
        //TODO CHECK MAZEBOARD SIZING VS HEIGHT/WIDTH

        this.mazeboard = mazeboard;
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;
        this.playerList = playerList;
        this.indexOfActive = indexOfActive;
        fixActive();
    }

    @Override
    public boolean rotateSpareTile(int degreesCW) {
        return mazeboard.rotateSpare(degreesCW);
    }


    private void checkShiftPlayers(int index, BoardOpt rorc, Direction dir) {
        for (int i = 0; i < playerList.size(); i++) {
            IRefPlayer temp = playerList.get(i);
            switch (rorc) {

                case Row:

                    if (temp.getCurrentRow() == index) {
                        temp.shiftPlayer(dir);
                    }
                    break;
                case Col:
                    if (temp.getCurrentColumn() == index) {
                        temp.shiftPlayer(dir);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean shiftAndInsert(int indexOfRowOrCol, BoardOpt rORc, Direction direction) {

        //TODO check before executing anything
        //TODO needs to be connected to rotate
        if (mazeboard.shiftRowCol(indexOfRowOrCol,rORc,direction)) {
            //shifts players on row/col and replaces them in the correct position
            checkShiftPlayers(indexOfRowOrCol, rORc, direction);

            //now update tiles of board
            switch (rORc) {
                case Row:
                    if (direction == Direction.Right) {
                        mazeboard.placeSpare(indexOfRowOrCol, 0);
                    }
                    else if (direction == Direction.Left) {
                        mazeboard.placeSpare(indexOfRowOrCol, mazeWidth-1);
                    }
                    break;
                case Col:
                    if (direction == Direction.Up) {
                        mazeboard.placeSpare(mazeHeight-1, indexOfRowOrCol);
                    }
                    else if (direction == Direction.Down) {
                        mazeboard.placeSpare(0, indexOfRowOrCol);
                    }
                    break;
                default:
                    throw new IllegalStateException("Illegal state, Gameboard permanently corrupted.");
            }
            return true;

        }
        return false;
    }


    @Override
    public boolean isReachableByActivePlayer(int rowTo, int colTo) {
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return mazeboard.isReachableFrom(activePlayer.getCurrentRow(), activePlayer.getCurrentColumn(), rowTo, colTo);
    }

    @Override
    public boolean isActiveOnGoal() {
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return activePlayer.isOnGoal();
    }

    @Override
    public boolean kickActivePlayer() {
        playerList.remove(indexOfActive);
        //ONLY ONE WAY THE INDEX BREAKS HERE ->
        //in the case index of deletion is 0, next player is 1, who becomes new index 1.
        //this is inductive up to final case in which:
        //if max index deleted-> maxindex is now length of list and out of bounds, must reset to zero
        //TODO CHECK FOR WHEN KICKED ALL PLAYERS -> END GAME?
        fixActive();
        return true;
    }

    @Override
    public boolean moveActiveTo(int rowNumber, int colNumber) {
        if (!(isReachableByActivePlayer(rowNumber, colNumber))) {
            return false;
        }
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return activePlayer.movePlayerTo(rowNumber, colNumber);
    }


    private void fixActive() {
        if (indexOfActive >= playerList.size()) {
            this.indexOfActive = 0;
        }
    }

}
