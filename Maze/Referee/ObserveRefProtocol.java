package Protocols;

import GameState.IRefGameState;
import Maze_Board.IBoardModel;
import Player_Move.IMove;

import java.util.List;
import java.util.Map;


/**
 * This ref protocol adds observation ability by being provided with an observer through GIVEOBSERVER.
 */
public class ObserveRefProtocol extends RefProtocol{
    private IObserver obs;


    public ObserveRefProtocol(List<IPlayerProtocol> lop, IBoardModel mb , IObserver obs) {
        super(lop, mb);
        this.obs = obs;
    }

    public ObserveRefProtocol(List<IPlayerProtocol> lop, IRefGameState gs, IObserver obs) {
        super(lop, gs);
        this.obs = obs;
    }

    public ObserveRefProtocol(List<IPlayerProtocol> lop, IRefGameState gs, IMove last , IObserver obs) {
        super(lop, gs, last);
        this.obs = obs;
    }



    protected void makeMoveForPlayer(IMove move) {
        super.makeMoveForPlayer(move);

        obs.receiveState(this.refState.getCopy());

    }

    public Map<Boolean, List<IPlayerProtocol>> runGameFromState(IRefGameState state) {
        obs.receiveState(this.refState.getCopy());
        Map<Boolean, List<IPlayerProtocol>> temp = super.runGameFromState(state);
        obs.gameOver();

        return temp;
    }


}
