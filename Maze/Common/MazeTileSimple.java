import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simple game tile, and implements necessary functionality.
 * TODO add ownership of a UNIQUE PAIR OF UNORDERED GEM - set?
 */
public class MazeTileSimple implements ITile {

    private ConnectorShape connectorShape;

    //ROTATION CLOCKWISE
    private int rotation=0;
    //A CHOICE OF 0 90 180 270



    /**
     * Constructor for the simple maze tile, just needs what this tile includes.
     * @param connectorShape The connector shape of this tile.
     */
    public MazeTileSimple(ConnectorShape connectorShape) {
        if (connectorShape == null) {
            throw new IllegalArgumentException();
        }
        this.connectorShape = connectorShape;
    }

    @Override
    public ConnectorShape getConnector() {
        return this.connectorShape;
    }

    @Override
    public void rotateConnector(int degreesCW) {
        if (outOfBound(degreesCW)) {
            throw new IllegalArgumentException("Degree not in bounds to rotate.");
        }
        this.rotation = (this.rotation+degreesCW) % 360;
    }

    @Override
    public boolean isEmpty() {
        return this.connectorShape == ConnectorShape.Empty;
    }

    @Override
    public List<Direction> getDirections() {
        ArrayList<Direction> dl = new ArrayList<>();

        switch (connectorShape) {
            case Tee:
                dl.add(Direction.Left);
                dl.add(Direction.Up);
                dl.add(Direction.Right);
                dl.add(Direction.Down);
                if (rotation == 0) {
                    dl.remove(Direction.Up);
                }
                if (rotation == 90) {
                    dl.remove(Direction.Right);
                }
                if (rotation == 180) {
                    dl.remove(Direction.Down);
                }
                if (rotation == 270) {
                    dl.remove(Direction.Left);
                }
                return dl;
            case UpLine:
                if (rotation == 90 || rotation == 270) {
                    dl.add(Direction.Left);
                    dl.add(Direction.Right);
                }
                else {
                    dl.add(Direction.Up);
                    dl.add(Direction.Down);
                }
                return dl;

            case Cross:
                dl.add(Direction.Left);
                dl.add(Direction.Up);
                dl.add(Direction.Right);
                dl.add(Direction.Down);
                return dl;

            case UpRight:
                if (rotation == 0) {
                    dl.add(Direction.Up);
                    dl.add(Direction.Right);
                }
                if (rotation == 90) {
                    dl.add(Direction.Right);
                    dl.add(Direction.Down);
                }
                if (rotation == 180) {
                    dl.add(Direction.Down);
                    dl.add(Direction.Left);
                }
                if (rotation == 270) {
                    dl.add(Direction.Left);
                    dl.add(Direction.Up);
                }
                return dl;


            case Empty:
                return dl;
            default:
                throw new IllegalStateException("Tile contains unknown connector shape");

        }
    }

    @Override
    public int getRotationCW() {
        return rotation;
    }

    private boolean outOfBound(int degree) {
        return (!(degree == 90 || degree == 180 || degree == 270 || degree == 0));
    }
}
