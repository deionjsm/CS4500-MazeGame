/**
 * This interface describes a strategy for an AI player.
 * It may request a move through this interface.
 * A player will HAVE A strategy, to which it passes the necessary data.
 */
public interface IStrategy {


    /**
     * Makes a decision based on an implementation's strategy, returning a well-formed move.
     * (NOT NECCESSARILY A LEGAL MOVE).
     * @return A well-formed, but not necessarily legal move.
     */
    IMove getDecision();

}
