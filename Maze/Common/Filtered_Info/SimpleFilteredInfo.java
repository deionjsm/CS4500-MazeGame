package Filtered_Info;

import Maze_Board.IBoardModel;
import Maze_Positions.IMazePosition;
import Maze_Tiles.ITile;
import Player_Move.IMove;

/**
 * A simple implementation of filtered information.
 */
public class SimpleFilteredInfo implements IFilteredInfo {

    private final IBoardModel board;
    private final ITile currentSpare;
    private final IMazePosition myPos;
    private final IMazePosition myGoal;
    private final IMazePosition myHome;
    //can be null NOTE
    private IMove lastMove;

    /**
     * SHOULD NOT BE PASSED A REFERENCE OF A USED MAZE POSITION.
     */
    public SimpleFilteredInfo(IBoardModel board,
                              ITile currentSpare,
                              IMazePosition myPos,
                              IMazePosition myGoal,
                              IMazePosition myHome) {
        this.board = board;
        this.currentSpare = currentSpare;
        this.myPos = myPos;
        this.myGoal = myGoal;
        this.myHome = myHome;

    }

    public SimpleFilteredInfo(IBoardModel board,
                              ITile currentSpare,
                              IMazePosition myPos,
                              IMazePosition myGoal,
                              IMazePosition myHome,
                              IMove lastMove) {
        this.board = board;
        this.currentSpare = currentSpare;
        this.myPos = myPos;
        this.myGoal = myGoal;
        this.myHome = myHome;
        this.lastMove = lastMove;

    }

    @Override
    public IBoardModel getCurrentBoard() {
        return this.board;
    }

    @Override
    public ITile getCurrentSpareTile() {
        return this.currentSpare;
    }

    @Override
    public IMazePosition getMyPosition() {
        return this.myPos;
    }

    @Override
    public IMazePosition getMyGoal() {
        return this.myGoal;
    }

    @Override
    public IMazePosition getMyHome() {
        return this.myHome;
    }

    @Override
    public IMove getLastMove() {
        return this.lastMove;
    }

    @Override
    public void setLastMove(IMove mov) {
        this.lastMove = mov;
    }


}
