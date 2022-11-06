package Protocols;

import GameState.IRefGameState;

import java.util.List;
import java.util.Map;

/**
 * The test-minded interface that our referee protocol will implement, guaranteeing running from a given state,
 * to completion.
 */
public interface IReferee {

    /**
     * This runs the game to completion from this state.
     * @param state all that is needed to resume game from interruption.
     */
    Map<Boolean, List<IPlayerProtocol>> runGameFromState(IRefGameState state);

}
