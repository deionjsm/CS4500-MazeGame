import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EuclidStrategy extends ASimpleStrategy {

    public EuclidStrategy(IFilteredInfo info) {
        super(info);
    }

    @Override
    List<IMazePosition> getSecondaryGoals() {
        List<IMazePosition> result = new ArrayList<>();
        HashMap<Integer, List<IMazePosition> > hm = new HashMap<>();
        //WILL DO EUCLIDEAN WITHOUT SQUARE ROOT -> ENOUGH TO FIND COMPARE
        //INTEGER REPRESENTS SQUARED ROW DIFF + SQUARED COL DIFF
        int myRow = info.getMyGoal().getXCoordinate();
        int myCol = info.getMyGoal().getYCoordinate();
        IBoardModel board = info.getCurrentBoard();
        for (int rows = 0; rows < board.getHeight(); rows++) {
            for (int cols = 0; cols < board.getWidth(); cols++) {
                int tempRowDiff = (myRow - rows) * (myRow - rows);
                int tempColDiff = (myCol - cols) * (myCol - cols);
                int sum = tempRowDiff + tempColDiff;
                if (hm.containsKey(sum)) {
                    List<IMazePosition> temp = hm.get(sum);
                    temp.add(new MazePositionSimple(rows, cols));
                    hm.put(sum, temp);
                }
                else {
                    List<IMazePosition> temp = new ArrayList<>();
                    temp.add(new MazePositionSimple(rows, cols));
                    hm.put(sum, temp);
                }
            }
        }
        //iterate thru hashmap looking for keys from low to high -> max distance is (height-1)^2 + (width-1)^2
        for (int distsqr = 0;
             distsqr < ((board.getHeight()-1)*(board.getHeight()-1)) + ((board.getWidth()-1)*(board.getWidth()-1));
             distsqr++) {
            if (hm.containsKey(distsqr)) {
                result.addAll(hm.get(distsqr));
            }
        }
        return result;
    }


}
