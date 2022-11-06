package GameState;

import Filtered_Info.IFilteredInfo;
import Filtered_Info.SimpleFilteredInfo;
import Maze_Board.IBoardModel;
import Enum_Constants.BoardOpt;
import Enum_Constants.Direction;
import Maze_Positions.MazePositionSimple;
import Maze_Tiles.ITile;
import Players.IRefPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * This concrete class implements the interface of a referee's gamestate in the classic 7 by 7 game.
 * This implementation uses a list of players and kicks by deleting the reference to player.
 */
public class State implements IRefGameState {

    private final IBoardModel mazeboard;

    //maze height is one more than the max index of a row -> furthest down
    private final int mazeHeight;

    //Maze width is one more than the max index of a column -> furthest right
    private final int mazeWidth;
    //TODO SORT BY AGE
    private final List<IRefPlayer> playerList;

    //determines active player
    private int indexOfActive;


    /**
     * Simple constructor setting board, given all needed and useful information
     */
    public State(IBoardModel mazeboard,
                 int mazeHeight, int mazeWidth,
                 List<IRefPlayer> playerList,
                 int indexOfActive) {
        //TODO CHECK MAZEBOARD SIZING VS HEIGHT/WIDTH

        this.mazeboard = mazeboard;
        this.mazeHeight = mazeHeight;
        this.mazeWidth = mazeWidth;
        this.playerList = playerList;
        this.indexOfActive = indexOfActive;
        fixActive();
    }

    @Override
    public boolean rotateSpareTile(int degreesCW) {
        return mazeboard.rotateSpare(degreesCW);
    }


    private void checkShiftPlayers(int index, BoardOpt rorc, Direction dir) {
        for (int i = 0; i < playerList.size(); i++) {
            IRefPlayer temp = playerList.get(i);
            switch (rorc) {

                case Row:

                    if (temp.getCurrentRow() == index) {
                        temp.shiftPlayer(dir);
                    }
                    break;
                case Col:
                    if (temp.getCurrentColumn() == index) {
                        temp.shiftPlayer(dir);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean shiftAndInsert(int indexOfRowOrCol, BoardOpt rORc, Direction direction) {

        //TODO check before executing anything
        //TODO needs to be connected to rotate
        if (mazeboard.shiftRowCol(indexOfRowOrCol,rORc,direction)) {
            //shifts players on row/col and replaces them in the correct position
            checkShiftPlayers(indexOfRowOrCol, rORc, direction);

            //now update tiles of board
            switch (rORc) {
                case Row:
                    if (direction == Direction.Right) {
                        mazeboard.placeSpare(indexOfRowOrCol, 0);
                    }
                    else if (direction == Direction.Left) {
                        mazeboard.placeSpare(indexOfRowOrCol, mazeWidth-1);
                    }
                    break;
                case Col:
                    if (direction == Direction.Up) {
                        mazeboard.placeSpare(mazeHeight-1, indexOfRowOrCol);
                    }
                    else if (direction == Direction.Down) {
                        mazeboard.placeSpare(0, indexOfRowOrCol);
                    }
                    break;
                default:
                    throw new IllegalStateException("Illegal state, Gameboard permanently corrupted.");
            }
            return true;

        }
        return false;
    }


    @Override
    public boolean isReachableByActivePlayer(int rowTo, int colTo) {
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return mazeboard.isReachableFrom(activePlayer.getCurrentRow(), activePlayer.getCurrentColumn(), rowTo, colTo);
    }

    @Override
    public boolean isActiveOnGoal() {
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return activePlayer.isOnGoal();
    }

    @Override
    public boolean kickActivePlayer() {
        playerList.remove(indexOfActive);
        //ONLY ONE WAY THE INDEX BREAKS HERE ->
        //in the case index of deletion is 0, next player is 1, who becomes new index 1.
        //this is inductive up to final case in which:
        //if max index deleted-> maxindex is now length of list and out of bounds, must reset to zero
        //TODO CHECK FOR WHEN KICKED ALL PLAYERS -> END GAME?
        fixActive();
        return true;
    }

    @Override
    public boolean moveActiveTo(int rowNumber, int colNumber) {
        if (!(isReachableByActivePlayer(rowNumber, colNumber))) {
            return false;
        }
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return activePlayer.movePlayerTo(rowNumber, colNumber);
    }

    @Override
    public boolean anyPlayersWon() {
        for (IRefPlayer p : playerList) {
            if (p.hasWon()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<IRefPlayer> allWinners() {
        List<IRefPlayer> winners = new ArrayList<>();
        if (anyPlayersWon()) {
            for (IRefPlayer p : playerList) {
                if (p.hasWon()) {
                    winners.add(p);
                }
            }
        }
        else { //check those that have reached goal at least... then sort by euclidean dist and cull
            int currentMin = 999999999; //TODO fix with more elgenat solution
            List<IRefPlayer> templist = new ArrayList<>();
            for (IRefPlayer p : playerList) {

                if (p.getHasReachedGoal() && EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                        p.getHomeRow(),p.getHomeCol()) == currentMin) {

                    templist.add(p); //ADD
                }
                if (p.getHasReachedGoal() && EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                        p.getHomeRow(),p.getHomeCol()) < currentMin) {

                    currentMin = EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                            p.getHomeRow(),p.getHomeCol());
                    templist = new ArrayList<>();
                    templist.add(p); //NEW LIST
                }
            }
            if (templist.size() >= 1) {
                return templist;
            }


            currentMin = 999999999; //TODO fix with more elegant solution
            templist = new ArrayList<>();
            for (IRefPlayer p : playerList) {

                if (EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                        p.getGoalRow(),p.getGoalCol()) == currentMin) {

                    templist.add(p); //ADD
                }
                if (EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                        p.getGoalRow(),p.getGoalCol()) < currentMin) {

                    currentMin = EuclidDist(p.getCurrentRow(),p.getCurrentColumn(),
                            p.getHomeRow(),p.getHomeCol());
                    templist = new ArrayList<>();
                    templist.add(p); //NEW LIST
                }
            }
            winners = templist;

        }


        return winners;
    }

    @Override
    public IBoardModel getBoardCopy() {
        return this.mazeboard.Filtered();
    }

    @Override
    public ITile getSpareCopy() {
        return this.mazeboard.getSpareCopy();
    }

    @Override
    public IFilteredInfo getFilteredInfo() {
        if (playerList.size() == 0) {
            throw new IllegalStateException();
        }
        IRefPlayer activePlayer = playerList.get(indexOfActive);
        return new SimpleFilteredInfo(this.getBoardCopy(), this.getSpareCopy(),
                new MazePositionSimple(activePlayer.getCurrentRow(),activePlayer.getCurrentColumn()),
                new MazePositionSimple(activePlayer.getGoalRow(),activePlayer.getGoalCol()),
                new MazePositionSimple(activePlayer.getHomeRow(),activePlayer.getHomeCol())
                );
    }

    @Override
    public String getNameOfActive() {
        return this.playerList.get(indexOfActive).getIdentifier();
    }

    @Override
    public void progressTurn() {
        this.indexOfActive = this.indexOfActive+1;
        fixActive();
        return;
    }

    @Override
    public void setGoalToHome() {
        this.playerList.get(indexOfActive).setGoalToHome();
    }

    @Override
    public IRefGameState getCopy() {
        return new State(this.getBoardCopy(),this.mazeHeight, this.mazeWidth, getPlayers(),this.indexOfActive);
    }

    /**
     * RETURNS A COPY OF PLAYERS
     * @return
     */
    @Override
    public List<IRefPlayer> getPlayers() {
        List<IRefPlayer> temp = new ArrayList<>();
        for (IRefPlayer p : playerList) {
            temp.add(p.getCopy());
        }
        return temp;
    }

    //returns the non square rooted euclidean distance for comparison purposes
    private int EuclidDist(int row, int col, int row2, int col2) {
        return (row - row2)*(row - row2) + (col - col2)*(col - col2);

    }


    private void fixActive() {
        if (indexOfActive >= playerList.size()) {
            this.indexOfActive = 0;
        }
    }



}
