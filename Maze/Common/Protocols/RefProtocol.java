package Protocols;

import Enum_Constants.BoardOpt;
import Enum_Constants.Direction;
import Filtered_Info.IFilteredInfo;
import Filtered_Info.SimpleFilteredInfo;
import GameState.IRefGameState;
import GameState.State;
import Maze_Board.IBoardModel;
import Player_Move.IMove;
import Players.IRefPlayer;
import Players.SimpleRefPlayer;

import java.util.*;


/** Represents the Referees side of the logical interactions between
 * Referee and Player protocol.
 * Runs game to completion.
 * returns winning player and misbehaving players.
 * "Explicitly state what kind of abnormal interactions that referee takes care of now":
 * The referee deals with THROWN EXCEPTIONS WHEN NOT EXPECTED and BREAKS THE RULES
 * does not deal with!!!!
 * TIMEOUTS!!!
 * Implements a method that runs game with given state.
 */
public class RefProtocol implements IReferee{

    // map of player
    private Map<String, IPlayerProtocol> mapPlayers;
    private List<IPlayerProtocol>  loPlayers;//todo depricate list

    private List<IPlayerProtocol> kicked;

    // State referee handles
    protected IRefGameState refState;

    // Keeps track of the number of ROUNDS that have passed
    private int roundCount = 0;

    // Keeps track of which player we are currently on
    private int playerIndex = 0;

    // number of passes
    private int playerPasses = 0;

    private IMove lastMove = null;

    //ASSUMED that list is pre sorted
    public RefProtocol(List<IPlayerProtocol> lop, IBoardModel mb) {
        if (lop.isEmpty()) {
            throw new IllegalArgumentException("List of Players in RefProtocol cannot be Empty");
        }
        this.loPlayers = lop;
        this.kicked = new ArrayList<>();
        mapPlayers = new HashMap<>();
        // Set Player's state
        for (IPlayerProtocol p : lop) { //will need to index with something UNQIUE AS WELL TODO
            String tempName;
            try {
                tempName = p.name();
            } catch (Exception e) {
                //IF THROW EXCEPTION -> KICK
                loPlayers.remove(p);
                kicked.add(p);
                //go to top of loop
                continue;
            }

            mapPlayers.put(tempName, p);
        }
        ArrayList<IRefPlayer> refPlayers = new ArrayList<>();
        for (IPlayerProtocol p : lop) {
            //TODO add functionality for randomness or picking
            //all immovable rows/cols at first per normal rules
            String tempName;
            try { //attempt to remove todo
                tempName = p.name();
            } catch (Exception e) {
                //IF THROW EXCEPTION -> KICK
                kicked.add(p);
                loPlayers.remove(p);
                continue; //go to top of loop
            }

            IRefPlayer temp = new SimpleRefPlayer(1,1,5,5,1,1,
                    mb.getHeight(), mb.getWidth(), tempName); //CHECKS NAME


            refPlayers.add(temp);


        }
        // TODO eventually support board proposal in referee, not needed in this assignment

        this.refState = new State(mb, 7,7, refPlayers,0);
    }



    public RefProtocol(List<IPlayerProtocol> lop, IRefGameState gs) {
        if (lop.isEmpty()) {
            throw new IllegalArgumentException("List of Players in RefProtocol cannot be Empty");
        }
        this.loPlayers = lop;
        this.kicked = new ArrayList<>();
        mapPlayers = new HashMap<>();
        // Set Player's state
        for (IPlayerProtocol p : lop) { //will need to index with something UNQIUE AS WELL TODO
            String tempName;
            try {
                tempName = p.name();
            } catch (Exception e) {
                //IF THROW EXCEPTION -> KICK
                loPlayers.remove(p);
                kicked.add(p);
                //go to top of loop
                continue;
            }

            mapPlayers.put(tempName, p);
        }

        this.refState = gs;
    }


    public RefProtocol(List<IPlayerProtocol> lop, IRefGameState gs, IMove last) {
        if (lop.isEmpty()) {
            throw new IllegalArgumentException("List of Players in RefProtocol cannot be Empty");
        }
        this.loPlayers = lop;
        this.kicked = new ArrayList<>();
        mapPlayers = new HashMap<>();
        // Set Player's state
        for (IPlayerProtocol p : lop) { //will need to index with something UNQIUE AS WELL TODO
            String tempName;
            try {
                tempName = p.name();
            } catch (Exception e) {
                //IF THROW EXCEPTION -> KICK
                loPlayers.remove(p);
                kicked.add(p);
                //go to top of loop
                continue;
            }

            mapPlayers.put(tempName, p);
        }
        this.lastMove = last;
        this.refState = gs;
    }




    //determines current "winners" essentially the closest people to winners.
    private List<IPlayerProtocol> winners() {
        List<IPlayerProtocol> result = new ArrayList<>();
        List<IRefPlayer> players = refState.allWinners();
        for (IPlayerProtocol realPlayer : loPlayers) {
            for (IRefPlayer p : players) {
                String tempName;
                try {
                    tempName = realPlayer.name();
                } catch (Exception e) {
                    //IF THROW EXCEPTION -> KICK
                    kicked.add(realPlayer);
                    loPlayers.remove(realPlayer);
                    //refState.
                    continue; //go to top of loop
                }
                if (p.getIdentifier().equals(tempName)) {
                    result.add(realPlayer);
                }
            }
        }
        //WE MUST IDENTIFY WHICH PLAYERS WON HERE -> DONE WITH NAME
        return result;
    }


    //determines whether game is completed from any of the three options.
    private boolean isGameComplete() {
        return anyPlayerHomeAfterGoal() || allPassed() || ranAllRounds() || this.loPlayers.size() == 0;
        //if player has reached home after visiting goal tile, all players passed this round, ref run 1000 rounds
    }

    private boolean ranAllRounds() {
        return roundCount >= 1000;
    }

    private boolean allPassed() {
        return playerPasses == loPlayers.size();
    }

    private boolean anyPlayerHomeAfterGoal() {
        return refState.anyPlayersWon();
    }


    // query and receive a move from the player
    private IMove queryForMove() {

        IFilteredInfo currentGameState = (refState.getFilteredInfo());
        //ADD LAST MOVE
        currentGameState.setLastMove(this.lastMove); //null sometimes!

        String nameOfActive = refState.getNameOfActive();
        // Request move to player
        IMove mov;
        try {
            mov = mapPlayers.get(nameOfActive).takeTurn(currentGameState);
        } catch (Exception e) {

            //kick player, here and state -> throw illegalmove exception;
            kicked.add(mapPlayers.get(nameOfActive));
            loPlayers.remove(mapPlayers.get(nameOfActive));
            mapPlayers.remove(nameOfActive);

            refState.kickActivePlayer();

            if (playerIndex == loPlayers.size()) {
                playerIndex = 0;
            }
            throw new IllegalCallerException();
        }
        return mov;

    }

    /*
    // checks for a skip
    private void makeSkipForPlayer() {

        // if player passes iterate the number of passes in a round
        playerPasses++;
        // if every player passed in a round
        if(playerPasses % loPlayers.size() == 0) {
            // TODO check for winner and produce exit state

        }

        // if it is the end of the round
        if(roundCount % loPlayers.size() == 0) {
            playerPasses = 0;
        }

        // Move on to the next player
        this.updatePlayerIndex();

        // Iterate to the next turn
        this.roundCount++;

        if(this.checkForThousandRounds()) {
            // TODO check for winner and produce exit state

        }
    }*/


    // make a move for a player
    void makeMoveForPlayer(IMove move) {
        if (move.getSkip()) {
            this.playerPasses++;
            this.updatePlayerIndex();
            return;
            //TODO DECIDE WHETHER LAST MOVE CAN BE PASS
        }
        //todo check if is last move and disallow
        // Get coordinates for move
        int r = move.getMoveAvatarPos().getXCoordinate();
        int c = move.getMoveAvatarPos().getYCoordinate();

        // Perform moves on the Ref's state
        refState.rotateSpareTile(move.getRotation());
        //todo refactor boardopt out
        if (move.getShiftDirection() == Direction.Left || move.getShiftDirection() == Direction.Right) {
            refState.shiftAndInsert(move.getShiftIndex(), BoardOpt.Row ,move.getShiftDirection());
        }
        else {
            refState.shiftAndInsert(move.getShiftIndex(), BoardOpt.Col ,move.getShiftDirection());
        }

        if (refState.isReachableByActivePlayer(r, c)) {
            refState.moveActiveTo(r, c);
        }

        // Check if Player has reached their goal tile
        if(refState.isActiveOnGoal() && !refState.anyPlayersWon()) {
            //set goal to home
            refState.setGoalToHome();

        }

        // Move to next player, update last move
        this.updatePlayerIndex();
        this.lastMove = move;

    }


    // cycle to the next player for us AND state
    private void updatePlayerIndex() {
        // move on to the next player
        this.playerIndex++;
        this.refState.progressTurn();
        // if we reach the end of a round, go back to first player
        if (playerIndex == loPlayers.size()) {
            playerIndex = 0;
        }
    }


    // check to see if the game has reached the referees 10000 round limit
    /*
    private boolean checkForThousandRounds() {
        int limit = 1000 * loPlayers.size();

        return roundCount * loPlayers.size() == limit;
    }*/

    @Override
    public Map<Boolean, List<IPlayerProtocol>> runGameFromState(IRefGameState state) {
        //startup
        //let next player make move
        //determine if move is legal, if yes commit change and index next player
        //send setup NONE if player ever
        this.refState = state;
        //TODO check cleanliness of state
        this.playerIndex = loPlayers.indexOf(mapPlayers.get(state.getNameOfActive()));
        if (playerIndex == -1) {
            List<IPlayerProtocol> tempWinners = sendWinners();
            Map<Boolean, List<IPlayerProtocol>> res = new HashMap<>();
            res.put(true, tempWinners);
            res.put(false, this.kicked);
            return res;
        }
        while (!isGameComplete()) { //while game is ongoing...
            IMove wantedMove;
            try {
                wantedMove = queryForMove();
            } catch (IllegalCallerException e) {
                //kicked player, move to top of loop;
                continue;
            }
            makeMoveForPlayer(wantedMove);
            if (playerIndex == 0) {
                //round over check if all passes are done, and break out of loop or reset passes
                if (playerPasses == loPlayers.size()) {
                    //send winners and end
                    List<IPlayerProtocol> tempWinners = sendWinners();
                    Map<Boolean, List<IPlayerProtocol>> res = new HashMap<>();
                    res.put(true, tempWinners);
                    res.put(false, this.kicked);
                    return res;
                }
                else {
                    //otherwise reset passes
                    playerPasses = 0;
                }
                roundCount++;//one round further!
            }


        }
        //send winners
        //and end
        List<IPlayerProtocol> tempWinners = sendWinners();
        Map<Boolean, List<IPlayerProtocol>> res = new HashMap<>();
        res.put(true, tempWinners);
        res.put(false, this.kicked);
        return res;
    }

    private List<IPlayerProtocol> sendWinners() {
        List<IPlayerProtocol> winners = this.winners();
        for (IPlayerProtocol p: loPlayers) {
            try {
                p.won(winners.contains(p));//TRUE IF IN WINNERS, FALSE OTHERWISE
            }
            catch (Exception e) {
                //simply return since doesn't matter! games over
            }
        }
        return winners;
    }


}
