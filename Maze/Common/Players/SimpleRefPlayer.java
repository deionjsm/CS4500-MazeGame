package Players;

import Enum_Constants.Direction;

import java.awt.*;

/**
 * This class implements a Player.IRefPlayer, and guarantees the functionality offered by a Maze player.
 */
public class SimpleRefPlayer implements IRefPlayer {

    String name;
    private int rowIndex;
    private int colIndex;

    private int goalRow;
    private int goalCol;

    private int homeRow;
    private int homeCol;

    private boolean hasReachedGoal = false;

    private Color avatarColor;

    //TODO ADD AVATAR REPRESENTATION

    private int boardHeight;
    private int boardWidth;

    /**
     * Simple constructor that sets starting position, goal position, and the size of board for self checking.
     */
    public SimpleRefPlayer(int rowIndex, int colIndex,
                           int goalRow,int goalCol,
                           int homeRow, int homeCol,
                           int boardHeight, int boardWidth) {

        //TODO CHECK WITHIN BOUNDS
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.goalRow = goalRow;
        this.goalCol = goalCol;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.homeRow = homeRow;
        this.homeCol = homeCol;
        this.avatarColor = Color.BLUE;
        this.name = "UNNAMED";
    }

    public SimpleRefPlayer(int rowIndex, int colIndex,
                           int goalRow,int goalCol,
                           int homeRow, int homeCol,
                           int boardHeight, int boardWidth, String name) {

        //TODO CHECK WITHIN BOUNDS
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.goalRow = goalRow;
        this.goalCol = goalCol;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.homeRow = homeRow;
        this.homeCol = homeCol;
        this.avatarColor = Color.BLUE;
        this.name = name;
    }


    public SimpleRefPlayer(int rowIndex, int colIndex,
                           int goalRow,int goalCol,
                           int homeRow, int homeCol,
                           int boardHeight, int boardWidth, String name, Color avatarColor) {

        //TODO CHECK WITHIN BOUNDS
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.goalRow = goalRow;
        this.goalCol = goalCol;
        this.boardHeight = boardHeight;
        this.boardWidth = boardWidth;
        this.homeRow = homeRow;
        this.homeCol = homeCol;
        this.avatarColor = avatarColor;
        this.name = name;
    }


    @Override
    public int getCurrentRow() {
        return rowIndex;
    }

    @Override
    public int getCurrentColumn() {
        return colIndex;
    }

    @Override
    public boolean isOnGoal() {
        return (goalRow == rowIndex && goalCol == colIndex);
    }

    @Override
    public boolean shiftPlayer(Direction direction) {
        switch (direction) {
            case Left:
                colIndex = colIndex-1;
                checkBoundsAndReplace();
                break;
            case Right:
                colIndex = colIndex+1;
                checkBoundsAndReplace();
                break;
            case Up:
                rowIndex = rowIndex-1;
                checkBoundsAndReplace();
                break;
            case Down:
                //DOWN IS INCREASE
                rowIndex = rowIndex+1;
                checkBoundsAndReplace();
                break;
        }
        return true;
    }

    @Override
    public Color getPlayerColor() {
        return this.avatarColor;
    }

    //overflows to the other side
    private void checkBoundsAndReplace() {
        if (rowIndex < 0) {
            rowIndex = boardHeight-1;
        }
        else if (rowIndex >= boardHeight) {
            rowIndex = 0;
        }
        else if (colIndex < 0) {
            colIndex = boardWidth-1;
        }
        else if (colIndex >= boardWidth) {
            colIndex = 0;
        }
    }

    @Override
    public int getGoalRow() {
        return goalRow;
    }

    @Override
    public int getGoalCol() {
        return goalCol;
    }

    @Override
    public int getHomeRow() {
        return homeRow;
    }

    @Override
    public int getHomeCol() {
        return homeCol;
    }

    @Override
    public boolean movePlayerTo(int row, int col) {
        //TODO check on bounds?
        this.rowIndex = row;
        this.colIndex = col;
        if (row == goalRow && col == goalCol) {
            this.hasReachedGoal = true;
        }
        return true;
    }

    public boolean getHasReachedGoal() {
        return hasReachedGoal;
    }

    public boolean hasWon() {
        return hasReachedGoal && (homeRow == rowIndex && homeCol == colIndex);
    }

    @Override
    public String getIdentifier() {
        return this.name;
    }

    @Override
    public void setGoalToHome() {
        this.goalRow = homeRow;
        this.goalCol = homeCol;

    }

    @Override
    public IRefPlayer getCopy() {
        return new SimpleRefPlayer(rowIndex, colIndex, goalRow, goalCol,
                homeRow, homeCol,
                boardHeight, boardWidth,
                this.name, this.avatarColor);
    }
}
