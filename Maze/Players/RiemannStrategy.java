package Players;

import Common.IBoardModel;
import Common.IMazePosition;
import Common.MazePositionSimple;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a riemann strategy which essentially attempts to move to the goal immediately
 * and if cant then checks for reach on top left to bottom right in order (keeping original goal in mind after any move)
 *
 */
public class RiemannStrategy extends Strategy {




    public RiemannStrategy(IFilteredInfo info) {
        super(info);
    }

    protected List<IMazePosition> getSecondaryGoals() {
        ArrayList<IMazePosition> result = new ArrayList<>();
        IBoardModel board = info.getCurrentBoard();
        for (int rows = 0; rows < board.getHeight(); rows++) {
            for (int cols = 0; cols < board.getWidth(); cols++) {
                result.add(new MazePositionSimple(rows,cols));
            }
        }
        return result;
    }
}
