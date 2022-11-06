import java.util.*;

/**
 * This class implements a simple Maze Board for Maze.com.
 * CONSTANTS:
 * The board is 2 dimensional.
 * The board has only positive position values.
 * The bounds minimum for rows and columns is 0.
 * Legal shift amounts are the same horizontally and vertically.
 * 0 , 0 is top left. Length-1 , Width-1 is bottom right.
 */
public class Board implements IBoardModel {

    //2d array of POSITIONS, each of which HAS a tile.
    //FINAL BECAUSE BOARD SIZE DOES NOT CHANGE IN GAME
    private final List< List<IMazePosition> > mazeBoard;
    //POSITION MUST ALIGN WITH INDEX IN LIST
    private final Queue<ITile> spareTiles;

    private final List<Integer> legalIndex;
    private final int height;
    private final int length;

    /**
     * Constructor with all givens
     * @param height max row index EXCLUSIVE. (essentially board height)
     * @param length max col index EXCLUSIVE. (essentially board width)
     * @param mazeBoard the board with positions placed.
     * @param spareTiles the sparetiles left to be placed (should be single with current implm).
     */
    public Board(int height,
                 int length,
                 List< List<IMazePosition> > mazeBoard,
                 Queue<ITile> spareTiles) {

        //TODO CHECK WHETHER LEGAL VALUES
        //TODO MAKE SURE POSITIONS X Y == LIST X Y
        this.legalIndex = new ArrayList<>();
        this.legalIndex.add(0);
        this.legalIndex.add(1);
        this.legalIndex.add(2);
        this.legalIndex.add(3);
        this.legalIndex.add(4);
        this.legalIndex.add(5);
        this.legalIndex.add(6);
        this.height = height;
        this.length = length;
        this.mazeBoard = mazeBoard;
        this.spareTiles = spareTiles;

    }

    /**
     * Convenience ctor - sets to 'default' vals.
     */
    public Board() {
        this.legalIndex = new ArrayList<>();
        this.legalIndex.add(0);
        this.legalIndex.add(1);
        this.legalIndex.add(2);
        this.legalIndex.add(3);
        this.legalIndex.add(4);
        this.legalIndex.add(5);
        this.legalIndex.add(6);
        this.height = 7;
        this.length = 7;
        this.mazeBoard = makeBoard(); //FUNCTION TO MAKE BOARD
        this.spareTiles = new ArrayDeque<>();
        this.spareTiles.add(new MazeTileSimple(ConnectorShape.Cross));
        //maybe random tile in future?
    }

    //currently has no randomness -> can be added by changing CONNECTORSHAPE>CROSS to a randomizer function
    private List< List<IMazePosition> > makeBoard() {
        List< List<IMazePosition> > out = new ArrayList<>();
        for (int rowNum = 0; rowNum < this.height; rowNum++) {
            List<IMazePosition> temp = new ArrayList<>();
            for (int colNum = 0; colNum < this.length; colNum++) {
                temp.add(new MazePositionSimple(rowNum, colNum, new MazeTileSimple(ConnectorShape.Cross)));
            }
            out.add(temp);
        }
        return out;
    }

    //Checks that x value is less than the furthest column, and greater than -1
    //Same for y but between the height and -1
    private boolean withinBounds(int xVal, int yVal) {
        return xVal < length && xVal >= 0 && yVal < height && yVal >= 0;
    }


    @Override
    public boolean shiftRowCol(int index, BoardOpt rORc, Direction direction) {

        if (!((rORc == BoardOpt.Row && (direction == Direction.Right || direction == Direction.Left))
                || (rORc == BoardOpt.Col && (direction == Direction.Up || direction == Direction.Down)))) {
            throw new IllegalArgumentException("Row and Direction missmatch");
        }
        if ((rORc == BoardOpt.Row && (index >= length|| index < 0))
                || (rORc == BoardOpt.Col && (index >= height|| index < 0))){
            return false;
        }
        if (!(legalIndex.contains(index))) {
            return false;
        }
        //CHECK DIRECTION IS LEGAL
        //if possible, then complete action
        //checks legality and all then delegates

        shiftTiles(index, direction);


        return true;
    }



    private void shiftTiles(int index, Direction direction) {
        //for all tiles in row/col, starting from direction index 0: (left-> ind,0)
        //make elements into spares
        //backfill using remaining: take (NON EMPTY) tiles and fill them in
        // in order moving direction to opposite direction
        //index is EITHER index of row (distance from top) or column (dist from left)

        int step;
        int startInd;
        int endInd;

        switch (direction) {
            case Left:
                step = -1;
                startInd = length-1;
                endInd = 0;


                shiftRow(index, endInd, startInd, step);
                break;
            case Right:
                step = 1;
                startInd = 0;
                endInd = length-1;
                shiftRow(index, endInd, startInd, step);
                break;
            case Up:
                step = -1;
                startInd = height-1;
                endInd = 0;

                shiftCol(index, endInd, startInd, step);
                break;
            case Down:
                step = 1;
                startInd = 0;
                endInd = height-1;

                shiftCol(index, endInd, startInd, step);
                break;
            default:
                throw new IllegalArgumentException("Unexpected Direction");

        }
    }

    /**
     *SHIFTS ROW BY 1
     */
    private void shiftRow(int index, int endInd, int startInd, int step) {



        spareTiles.add(mazeBoard.get(index).get(endInd).getTile());
        //WILL END WITH THIS AS CURRENT TILE, COULD USE INSTEAD
        endInd = endInd + step;

        ITile currentTile = new MazeTileSimple(ConnectorShape.Empty);
        for (int x = startInd; x != endInd; x+=step) {
            //using the fact that old val will be used, then updated
            currentTile = mazeBoard.get(index).get(x).swapTile(currentTile);
        }
    }

    /**
     * SHIFTS COL BY 1
     */
    private void shiftCol(int index, int endInd, int startInd, int step) {

        spareTiles.add(mazeBoard.get(endInd).get(index).getTile());

        endInd = endInd + step;
        ITile currentTile = new MazeTileSimple(ConnectorShape.Empty);
        for (int x = startInd; x != endInd; x+=step) {

            currentTile = mazeBoard.get(x).get(index).swapTile(currentTile);
        }
    }


    @Override
    public boolean rotateSpare(int degreesCW) {
        //check rotation legal
        if (degreesCW != 90 && degreesCW != 180 && degreesCW !=270 && degreesCW !=0) {
           return false;
        }
        if (spareTiles.isEmpty()) {
            throw new IllegalStateException("There are no spares to rotate.");
        }
        spareTiles.peek().rotateConnector(degreesCW);
        return true;
    }

    @Override
    public boolean placeSpare(int x, int y) {

        //failfast - does not check second if fails first
        if (!(withinBounds(x,y) && mazeBoard.get(x).get(y).getTile().isEmpty())) {
            return false;
        }
        mazeBoard.get(x).get(y).swapTile(spareTiles.remove());
        return true;
    }

    @Override
    public boolean isReachableFrom(int xFrom, int yFrom, int xTo, int yTo) throws IllegalArgumentException {
        //use connectors to determine where to go from current block:
        //if has no BACK CONNECTION ON NEXT, DENY PATH
        //when found path, return true, else when ran out of connections, return false
        if (!(withinBounds(xFrom, yFrom) && withinBounds(xTo, yTo))) {
            throw new IllegalArgumentException("Illegal coordinates to check reachable.");
        }



        return BFSSearch(xFrom,yFrom, xTo, yTo);
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getWidth() {
        return this.length;
    }


    //TODO TEST THIS
    @Override
    public void undoMove(IMove move) {
        if (move.getSkip() || (!(move.doShiftAndInsert()))) {
            return; //DO NOTHING TO A SKIPPED MOVE or no shift
        }
        //move.getRotation();
        int index = move.getShiftIndex();
        Direction dir = move.getShiftDirection();
        BoardOpt rc;
        if (dir == Direction.Right || dir == Direction.Left) {
            rc = BoardOpt.Row;
        }
        else {
            rc = BoardOpt.Col;
        }
        //to reverse, do opposite shift, insert with no rotation, and reset rotation of new spare to 0
        int rotation;
        switch (dir) {
            case Up:
                this.shiftRowCol(index,rc,Direction.Down);
                this.placeSpare(0,index);
                rotation = 360 - this.spareTiles.peek().getRotationCW();
                this.rotateSpare(rotation);
                return;
            case Down:
                this.shiftRowCol(index,rc,Direction.Up);
                this.placeSpare(this.height-1,index);
                rotation = 360 - this.spareTiles.peek().getRotationCW();
                this.rotateSpare(rotation);
                return;

            case Left:
                this.shiftRowCol(index,rc,Direction.Right);
                this.placeSpare(index,0);
                rotation = 360 - this.spareTiles.peek().getRotationCW();
                this.rotateSpare(rotation);
                return;

            case Right:
                this.shiftRowCol(index,rc,Direction.Left);
                this.placeSpare(index,this.length-1);
                rotation = 360 - this.spareTiles.peek().getRotationCW();
                this.rotateSpare(rotation);
                return;
            default:
                throw new IllegalStateException();

        }


    }

    @Override
    public boolean attemptMove(IMove move) {
        if (move.getSkip()) {
            return true;
        }
        if (!(move.doShiftAndInsert())) {
            return true;
        }

        int index = move.getShiftIndex();
        Direction dir = move.getShiftDirection();
        BoardOpt rc;
        if (dir == Direction.Right || dir == Direction.Left) {
            rc = BoardOpt.Row;
        }
        else {
            rc = BoardOpt.Col;
        }

        //otherwise do rotate, shift and insert;
        if (this.rotateSpare(move.getRotation())) {
            // have already rotated MUST UNDO IF WITHIN FAILS

            if (shiftRowCol(index,rc,dir)) {

                if (dir == Direction.Right) {
                    placeSpare(index, 0);
                } else if (dir == Direction.Left) {
                    placeSpare(index, this.length - 1);
                } else if (dir == Direction.Up) {
                    placeSpare(this.height - 1, index);
                } else if (dir == Direction.Down) {
                    placeSpare(0, index);
                }
                return true;
            } // COULDNT SHIFT -> UNDO ROTATE AND FAIL

            this.rotateSpare(360 - move.getRotation());

        }

        return false;
    }

    private boolean BFSSearch(int xFrom, int yFrom, int xTo, int yTo) {
        //gets directions from initial, iterate through list
        //check corresponding positions - IF VALID
        //if valid then ALSO CHECK if bidirectional - left for a right, right for a left, up for down, etc.
        //if YES
        // CHECK IF WANTED VALUE - YES RETURN TRUE
        // IF NO CHECK IF VISITED ALREADY -- YES DISPOSE
        //IF NO add to queue
        //when done first element, query next in queue
        //if queue empty, return false

        if (xFrom == xTo && yFrom == yTo) {
            return true;
        }
        ArrayList<IMazePosition> visited = new ArrayList<IMazePosition>();
        IMazePosition basePos;


        Queue<IMazePosition> bfsq = new ArrayDeque<>();

        bfsq.add(mazeBoard.get(xFrom).get(yFrom));

        while (!(bfsq.isEmpty())) {

            //redundant first time
            basePos = bfsq.remove();
            visited.add(basePos);
            List<Direction> dirList = basePos.getPathsDirections();

            IMazePosition tempPos;
            for (int ind = 0; ind < dirList.size(); ind++) {

                Direction dir = dirList.get(ind);

                //NEVER NULL IF PAST TRY CATCH!
                try {
                    tempPos = getPositionToThe(basePos, dir);
                } catch (IllegalArgumentException e) {
                    //IF ILLEGAL ARGUMENT -> NOT IN BOUNDS, SO CONTINUE TO NEXT dir
                    continue;
                }

                //DOES THE NEXT POSITION HAVE BIDIRECTIONAL
                if (tempPos.getPathsDirections().contains(complementDirection(dir))) {

                    if (tempPos.getXCoordinate() == xTo && tempPos.getYCoordinate() == yTo) {
                        return true;
                    }

                    if (!(visited.contains(tempPos))) {

                        bfsq.add(tempPos);
                    }//in visited --loop ignore
                } //does not contain bidirectionality

            }

        }

        return false;
    }




    //HELPER - GETS POSITION TO THE __ DIRECTION OF BASE POS
    //if out of bounds throws exception
    private IMazePosition getPositionToThe(IMazePosition basePos, Direction dir) {


        switch (dir) {
            case Up:
                if (!(withinBounds(basePos.getXCoordinate()-1, basePos.getYCoordinate()))) {
                    throw new IllegalArgumentException();
                }
                return mazeBoard.get(basePos.getXCoordinate()-1).get(basePos.getYCoordinate());
            case Down:
                if (!(withinBounds(basePos.getXCoordinate()+1, basePos.getYCoordinate()))) {
                    throw new IllegalArgumentException();
                }
                return mazeBoard.get(basePos.getXCoordinate()+1).get(basePos.getYCoordinate());
            case Right:
                if (!(withinBounds(basePos.getXCoordinate(), basePos.getYCoordinate()+1))) {
                    throw new IllegalArgumentException();
                }
                return mazeBoard.get(basePos.getXCoordinate()).get(basePos.getYCoordinate()+1);
            case Left:
                if (!(withinBounds(basePos.getXCoordinate(), basePos.getYCoordinate()-1))) {
                    throw new IllegalArgumentException();
                }
                return mazeBoard.get(basePos.getXCoordinate()).get(basePos.getYCoordinate()-1);
            default:
                throw new IllegalStateException("Illegal direction received from position.");
        }
    }

    private Direction complementDirection(Direction dir) {
        switch (dir) {
            case Up:
                return Direction.Down;
            case Down:
                return Direction.Up;
            case Left:
                return Direction.Right;
            case Right:
                return Direction.Left;
            default:
                throw new IllegalArgumentException();
        }
    }

    //MAKE VIEWABLE FOR TESTING -  get image / image analog
    protected List< List<IMazePosition> > getState() {
        return mazeBoard;
    }
    protected ITile getSpare() {
        return spareTiles.peek();
    }



}
