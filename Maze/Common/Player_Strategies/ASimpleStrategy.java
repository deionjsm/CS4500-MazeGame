package Player_Strategies;

import Maze_Board.IBoardModel;
import Maze_Positions.IMazePosition;
import Maze_Positions.MazePositionSimple;
import Enum_Constants.Direction;
import Filtered_Info.IFilteredInfo;
import Player_Move.IMove;
import Player_Move.SimpleMove;

import java.util.List;

/**
 * This abstract class minimizes duplication and forms a basis for simple, deterministic strategies.
 */
public abstract class ASimpleStrategy implements IStrategy {



    //IFilteredInfo info;

    public ASimpleStrategy() {

    }

    abstract List<IMazePosition> getSecondaryGoals(IFilteredInfo info);


    public IMove getDecision(IFilteredInfo info) {
        if (info == null) {
            throw new IllegalArgumentException();
        }
        int myRow = info.getMyPosition().getXCoordinate();
        int myCol = info.getMyPosition().getYCoordinate();
        int goalRow = info.getMyGoal().getXCoordinate();
        int goalCol = info.getMyGoal().getYCoordinate();
        IBoardModel board = info.getCurrentBoard();
        //if (board.isReachableFrom(myRow,myCol,goalRow,goalCol)) {
        //    return new SimpleMove(new MazePositionSimple(goalRow, goalCol )); //placeholder mazeposition
        //}
        //if cannot -> find secondary goal using method
        List<IMazePosition> secondaryGoals = getSecondaryGoals(info);

        Direction moveShift;
        int shiftIndex;

        //then test all possibilities of a move and see what they would result in
        for (IMazePosition pos : secondaryGoals) {
            /*if (pos.getXCoordinate() == myRow && pos.getYCoordinate() == myCol) {
                continue;
            }*/
            //attempt to slide each row from top to bottom, left first then right, then up and down shifts
            //do unique move
            for (int rowIndex = 0; rowIndex < info.getCurrentBoard().getHeight(); rowIndex++) {
                //ALL SHIFTS, LEFT AND RIGHT at all indicies
                shiftIndex = rowIndex;
                moveShift = Direction.Left;
                IMove tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if ((!(tempMove.getSkip())) && isNotLastMove(tempMove, info)) {
                    return tempMove;
                }

                moveShift = Direction.Right;
                tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if ((!(tempMove.getSkip())) && isNotLastMove(tempMove, info)) {
                    return tempMove;
                }

            }

            for (int colIndex = 0; colIndex < info.getCurrentBoard().getWidth(); colIndex++) {
                //ALL SHIFTS, UP N DOWN at all indicies
                shiftIndex = colIndex;
                moveShift = Direction.Up;
                IMove tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if ((!(tempMove.getSkip())) && isNotLastMove(tempMove, info)) {
                    return tempMove;
                }

                moveShift = Direction.Down;
                tempMove = testAllMoves(board, pos, shiftIndex,moveShift,myRow,myCol,goalRow,goalCol);
                if ((!(tempMove.getSkip())) && isNotLastMove(tempMove, info)) {
                    return tempMove;
                }
            }

        }

        //a skip if none found!!!!
        return new SimpleMove();
    }


    private boolean isNotLastMove(IMove mov, IFilteredInfo info) {//add to ifs above
        IMove last = info.getLastMove();
        if (last == null || last.getSkip()) {
            return true;
        }
        if (last.getShiftIndex() == mov.getShiftIndex()) {
            switch (mov.getShiftDirection()) {
                case Left:
                    return !(last.getShiftDirection() == Direction.Right);
                case Right:
                    return !(last.getShiftDirection() == Direction.Left);
                case Up:
                    return !(last.getShiftDirection() == Direction.Up);
                case Down:
                    return !(last.getShiftDirection() == Direction.Down);
            }
        }
        //if not same indicies, true
        return true;//if reverse direction and same index -> is reversing slide action and CANNOT BE DONE
    }

    //overflows to the other side if needed
    private int checkBoundsAndReplace(int index, int dimension) {
        if (index < 0) {
            return dimension-1;
        }
        else if (index >= dimension) {
            return 0;
        }
        return index;
    }
    private IMove testAllMoves(IBoardModel board, IMazePosition pos,
                               int shiftIndex, Direction moveShift,
                               int myRow, int myCol, int goalRow, int goalCol) {
        int rotationCW;
        //TODO IF MOVE MOVES PLAYERS CURRENT ROW/COL -> CHECK CAN BE REACHED FROM NEW POS
        for (int rot = 0; rot <= 270; rot = rot + 90) {//ALL ROTATES
            //would doing this move make it possible to reach
            rotationCW = rot;
            //attempt move, attempt reach, if yes, send move else undo and continue
            IMove tempMove = new SimpleMove(shiftIndex,moveShift, rotationCW, new MazePositionSimple(goalRow,goalCol));
            if (board.attemptMove(tempMove)) {
                //if here LEGAL MOVE AND COMPLETED
                //check if need to change myrow/mycol ->
                int tempRow = myRow;
                int tempCol = myCol;
                if (moveShift == Direction.Left) { //lateral shift
                    if (myRow == shiftIndex) {
                        tempCol =  checkBoundsAndReplace(tempCol-1, board.getWidth());
                    }
                }
                if (moveShift == Direction.Right) { //lateral shift
                    if (myRow == shiftIndex) {
                        tempCol =  checkBoundsAndReplace(tempCol+1, board.getWidth());
                    }
                }
                if (moveShift == Direction.Up) { //lateral shift
                    if (myCol == shiftIndex) {
                        tempRow =  checkBoundsAndReplace(tempRow-1, board.getHeight());
                    }
                }
                if (moveShift == Direction.Down) { //lateral shift
                    if (myCol == shiftIndex) {
                        tempRow =  checkBoundsAndReplace(tempRow+1, board.getHeight());
                    }
                }
                //NOW MUST CHECK IF IS ACTUALLY NOT MOVING ->

                //GOAL BY DEFINITION IS NOT SHIFTABLE, NO NEED TO CHECK
                if (board.isReachableFrom(tempRow,tempCol,goalRow,goalCol)
                        && !(tempRow == goalRow && tempCol == goalCol)) {
                    return new SimpleMove(shiftIndex, moveShift,rotationCW, new MazePositionSimple(goalRow,goalCol));
                }

                if (board.isReachableFrom(tempRow,tempCol,pos.getXCoordinate(), pos.getYCoordinate())
                        && !(tempRow == pos.getXCoordinate() && tempCol == pos.getYCoordinate())) {
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
