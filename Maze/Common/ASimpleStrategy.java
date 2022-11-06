import java.util.List;

/**
 * This abstract class minimizes duplication and forms a basis for simple, deterministic strategies.
 */
public abstract class ASimpleStrategy implements IStrategy {



    IFilteredInfo info;

    public ASimpleStrategy(IFilteredInfo info) {
        if (info == null) {
            throw new IllegalArgumentException();
        }
        this.info = info;
    }

    abstract List<IMazePosition> getSecondaryGoals();


    public IMove getDecision() {
        int myRow = info.getMyPosition().getXCoordinate();
        int myCol = info.getMyPosition().getYCoordinate();
        int goalRow = info.getMyGoal().getXCoordinate();
        int goalCol = info.getMyGoal().getYCoordinate();
        IBoardModel board = info.getCurrentBoard();
        if (board.isReachableFrom(myRow,myCol,goalRow,goalCol)) {
            return new SimpleMove(new MazePositionSimple(goalRow, goalCol )); //placeholder mazeposition
        }
        //if cannot -> find secondary goal using method
        List<IMazePosition> secondaryGoals = getSecondaryGoals();

        Direction moveShift;
        int shiftIndex;

        //then test all possibilities of a move and see what they would result in
        for (IMazePosition pos : secondaryGoals) {
            //attempt to slide each row from top to bottom, left first then right, then up and down shifts
            //do unique move
            for (int rowIndex = 0; rowIndex < info.getCurrentBoard().getHeight(); rowIndex++) {
                //ALL SHIFTS, LEFT AND RIGHT at all indicies
                shiftIndex = rowIndex;
                moveShift = Direction.Left;
                IMove tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if (!(tempMove.getSkip())) {
                    return tempMove;
                }

                moveShift = Direction.Right;
                tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if (!(tempMove.getSkip())) {
                    return tempMove;
                }

            }

            for (int colIndex = 0; colIndex < info.getCurrentBoard().getWidth(); colIndex++) {
                //ALL SHIFTS, UP N DOWN at all indicies
                shiftIndex = colIndex;
                moveShift = Direction.Up;
                IMove tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if (!(tempMove.getSkip())) {
                    return tempMove;
                }

                moveShift = Direction.Down;
                tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if (!(tempMove.getSkip())) {
                    return tempMove;
                }
            }

        }

        //a skip if none found!!!!
        return new SimpleMove();
    }


    private IMove testAllMoves(IBoardModel board, IMazePosition pos,
                               int shiftIndex, Direction moveShift,
                               int myRow, int myCol, int goalRow, int goalCol) {
        int rotationCW;
        for (int rot = 0; rot <= 270; rot = rot + 90) {//ALL ROTATES
            //would doing this move make it possible to reach
            rotationCW = rot;
            //attempt move, attempt reach, if yes, send move else undo and continue
            IMove tempMove = new SimpleMove(shiftIndex,moveShift, rotationCW, new MazePositionSimple(goalRow,goalCol));
            if (board.attemptMove(tempMove)) {
                //if here LEGAL MOVE AND COMPLETED
                if (board.isReachableFrom(myRow,myCol,goalRow,goalCol)) {
                    return new SimpleMove(shiftIndex, moveShift,rotationCW, new MazePositionSimple(goalRow,goalCol));
                }
                if (board.isReachableFrom(myRow,myCol,pos.getXCoordinate(), pos.getYCoordinate())) {
                    return new SimpleMove(shiftIndex, moveShift,rotationCW,
                            new MazePositionSimple(pos.getXCoordinate(), pos.getYCoordinate()));
                }
                //NO REACH? UNDO
                board.undoMove(tempMove);
            }
            //illegal move, not completed, continue
        }

        //IF NONE FOUND, SEND A SKIP
        return new SimpleMove();
    }
}
