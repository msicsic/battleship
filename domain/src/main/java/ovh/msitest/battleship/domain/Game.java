package ovh.msitest.battleship.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael on 11/09/2015.
 */
public class Game {
    private PlayerId player1;
    private PlayerId player2;

    public enum Status {
        WAITING_PLAYER1, WAITING_PLAYER2, READY_TO_PLACE, WAITING_VALIDATION_PL1, WAITING_VALIDATION_PL2
    }

    public Game() {
    }

    public PlayerId join() throws GameException {
        if (player1 != null && player2 != null) {
            throw new GameException("both players already joined the game");
        }
        PlayerId player = new PlayerId();
        if (player1 == null) {
            player1 = player;
        } else {
            player2 = player;
        }
        return player;
    }

    public boolean playersJoined() {
        return player1 != null && player2 != null;
    }

    public Status getStatus() {
        if (player1 == null) return Status.WAITING_PLAYER1;
        else if (player2 == null) return Status.WAITING_PLAYER2;
        else return Status.READY_TO_PLACE;
    }

    public PlacingPhase startPlacingPhase() throws GameException {
        if (getStatus() != Status.READY_TO_PLACE) throw new GameException("Both players must join the game before starting");
        return new PlacingPhase(player1, player2);
    }
}
