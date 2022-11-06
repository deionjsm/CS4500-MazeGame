package Maze_Positions;

import Enum_Constants.Direction;
import Maze_Tiles.ITile;

import java.util.List;

/**
 * This is an interface that describes what a maze position can offer.
 * Maze requires a semblance of position with respect to other elements.
 * However, since maze tiles positions change often (turn by turn), abstracting
 * it and providing methods for use by the referee is useful.
 */
public interface IMazePosition {

    /**
     * Returns the tile of this position.
     * NOTE: it returns the tiles ACTUAL OBJECT that can be edited.
     * This method does not produce a copy.
     * @return the tile value of this position.
     */
    ITile getTile();


    /**
     * SIDE EFFECT: Removes and replaces current tile of this position with given.
     * Note: can be given the empty tile to essentially 'empty' position.
     * @param tile tile to replace with.
     * @return ITILE that was replaced.
     */
    ITile swapTile(ITile tile);




    /**
     * Method that gives the internal data of the row's coordinate.
     * @return an integer of what position on a row this position is.
     */
    int getXCoordinate();

    /**
     * Method that gives the internal data of the Col's coordinate.
     * @return an integer of what position on a col this position is.
     */
    int getYCoordinate();


    /**
     * Method that gives the directions of where this element connects.
     * Note that for a true connection between two positions there must be BIDIRECTIONAL path direction.
     * @return a list of directions that return FROM REFERENCE OF THIS POSITION where it connects.
     */
    List<Direction> getPathsDirections();

    IMazePosition getCopy();
}
