package Protocols;

import GameState.IRefGameState;

/**
 * The api an observer promises to uphold.
 */
public interface IObserver {

    void receiveState(IRefGameState s);

    void gameOver();


}
