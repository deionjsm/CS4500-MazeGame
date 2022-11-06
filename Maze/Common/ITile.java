import java.util.List;

/**
 * This interface describes the necessary functionality of a tile in the Maze game.
 * TODO implement ownership of gems
 */
public interface ITile {

    /**
     * Gets the connector shape for this element.
     * @return an enumeration representing the connector of this tile.
     */
    ConnectorShape getConnector();

    /**
     * Rotates the current position by given amount of degrees, if legal.
     * @param degreesCW degrees to be rotated.
     */
    void rotateConnector(int degreesCW);

    /**
     * Queries whether this tile is currently an "empty" tile.
     * @return True if empty. False otherwise.
     */
    boolean isEmpty();

    /**
     * Method that gives the directions of where this tile connects.
     * @return list of directions.
     */
    List<Direction> getDirections();


    int getRotationCW();
}
