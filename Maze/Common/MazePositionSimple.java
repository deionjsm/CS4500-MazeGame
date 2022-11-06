import java.util.List;

/**
 * A simple implementation of what a Maze Position is:
 * basically, an x and y coordinate.
 * X IS ROW Y IS COL
 * Each position holds a Tile - which includes the possibility of an empty tile.
 */
public class MazePositionSimple implements IMazePosition {


    private final int xCoord;
    private final int yCoord;

    //can have a tile
    private ITile tile;




    /**
     * Constructor for simple position. Requires position and bounds.
     * @param x
     * @param y
     *
     */
    public MazePositionSimple(int x, int y, ITile tile) {

        this.xCoord = x;
        this.yCoord = y;

        //RANDOM TILE - EXCLUDING EMPTY
        this.tile = tile;

    }

    public MazePositionSimple(int row, int col) {

        this.xCoord = row;
        this.yCoord = col;

        //RANDOM TILE - EXCLUDING EMPTY
        this.tile = new MazeTileSimple(ConnectorShape.Empty);

    }

    @Override
    public ITile getTile() {
        return this.tile;
    }

    @Override
    public ITile swapTile(ITile tile) {
        ITile temp = this.tile;
        this.tile = tile;
        return temp;
    }

    @Override
    public int getXCoordinate() {
        return this.xCoord;
    }

    @Override
    public int getYCoordinate() {
        return this.yCoord;
    }

    @Override
    public List<Direction> getPathsDirections() {
        return this.tile.getDirections();
    }
}
