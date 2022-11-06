/**
 * A simple implementation of what a Move is.
 */
public class SimpleMove implements IMove{

    private boolean skip;
    private int shiftIndex;
    private Direction shiftDirection;
    private int rotationCW;
    private IMazePosition moveToPos;



    public SimpleMove(int shiftIndex,
                      Direction shiftDirection,
                      int rotationCW,
                      IMazePosition moveToPos) {
        if (shiftDirection == null || moveToPos == null || (rotationCW != 0
                && rotationCW != 90 && rotationCW != 180 && rotationCW != 270)) {
            throw new IllegalArgumentException();
        }

        this.shiftIndex = shiftIndex;
        this.shiftDirection = shiftDirection;

        this.rotationCW = rotationCW;

        this.moveToPos = moveToPos;
        this.skip = false;
    }


    public SimpleMove(IMazePosition moveToPos) {

        this.skip = false;

        this.shiftIndex = -1;
        this.shiftDirection = null;
        this.rotationCW = -1;

        this.moveToPos = moveToPos;
    }

    //THE SKIP CONSTRUCTOR
    public SimpleMove() {

        this.skip = true;

        this.shiftIndex = -1;
        this.shiftDirection = null;
        this.rotationCW = -1;

        this.moveToPos = moveToPos;
    }


    @Override
    public boolean getSkip() {
        return this.skip;
    }

    @Override
    public boolean doShiftAndInsert() {
        return !(shiftDirection == null || shiftIndex < 0);
    }

    @Override
    public int getShiftIndex() throws IllegalStateException {
        return this.shiftIndex;
    }

    @Override
    public Direction getShiftDirection() throws IllegalStateException {
        return this.shiftDirection;
    }

    @Override
    public int getRotation() throws IllegalStateException {
        return this.rotationCW;
    }

    @Override
    public IMazePosition getMoveAvatarPos() throws IllegalStateException {
        return this.moveToPos;
    }




}
