package ovh.msitest.battleship.domain;

import ovh.msitest.battleship.domain.Coordinate.Column;
import ovh.msitest.battleship.domain.Coordinate.Line;

import java.util.List;
import java.util.Map;

/**
 * Created by Mael on 14/09/2015.
 */
public class ShootingPhase {
    PlayerId player1 = new PlayerId();
    PlayerId player2 = new PlayerId();
    PlayerId currentPlayer = player1;
    List<PlacedBoat> player1Placements;
    List<PlacedBoat> player2Placements;
    // TODO : map de cells occupées

    public ShootingPhase(List<PlacedBoat> player1Placements, List<PlacedBoat> player2Placements) {
        this.player1Placements = player1Placements;
        this.player2Placements = player2Placements;
    }

    public FireResult fire(PlayerId playerId, Column col, Line line) {
        // TODO
        return new FireResult(FireResult.Status.MISS, null);
    }

    public PlayerId getPlayer1() {
        return player1;
    }

    public PlayerId getPlayer2() {
        return player2;
    }

    public PlayerId getCurrentPlayer() {
        return currentPlayer;
    }
}
