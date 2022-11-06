package Players;

import Common.IBoardModel;
import Common.IMazePosition;
import Common.ITile;
import Players.IFilteredInfo;

/**
 * A simple implementation of filtered information.
 */
public class SimpleFilteredInfo implements IFilteredInfo {

    private final IBoardModel board;
    private final ITile currentSpare;
    private final IMazePosition myPos;
    private final IMazePosition myGoal;
    private final IMazePosition myHome;

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
}
