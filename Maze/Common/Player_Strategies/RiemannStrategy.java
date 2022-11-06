package Player_Strategies;

import Maze_Board.IBoardModel;
import Maze_Positions.IMazePosition;
import Maze_Positions.MazePositionSimple;
import Filtered_Info.IFilteredInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a riemann strategy which essentially attempts to move to the goal immediately
 * and if cant then checks for reach on top left to bottom right in order (keeping original goal in mind after any move)
 *
 */
public class RiemannStrategy extends ASimpleStrategy{




    public RiemannStrategy() {
        super();
    }

    protected List<IMazePosition> getSecondaryGoals(IFilteredInfo info) {
        ArrayList<IMazePosition> result = new ArrayList<>();
        result.add(info.getMyGoal()); //ADDS GOAL AS HIGHEST PRIO
        IBoardModel board = info.getCurrentBoard();
        for (int rows = 0; rows < board.getHeight(); rows++) {
            for (int cols = 0; cols < board.getWidth(); cols++) {
                result.add(new MazePositionSimple(rows,cols));
            }
        }
        return result;
    }
}
