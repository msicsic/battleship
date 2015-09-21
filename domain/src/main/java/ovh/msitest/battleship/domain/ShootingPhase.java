package ovh.msitest.battleship.domain;

import ovh.msitest.battleship.domain.Coordinate.Column;
import ovh.msitest.battleship.domain.Coordinate.Line;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mael on 14/09/2015.
 */
public class ShootingPhase {
    PlayerId player1;
    PlayerId player2;
    PlayerId currentPlayer;
    Map<Coordinate, Boat> player1Placements;
    Map<Coordinate, Boat> player2Placements;
    Map<Coordinate, FireResult> player1Shoots = new HashMap<>();
    Map<Coordinate, FireResult> player2Shoots = new HashMap<>();
    Map<Boat, Integer> player1TouchedBoats = new HashMap<>();
    Map<Boat, Integer> player2TouchedBoats = new HashMap<>();
    Map<BoatName, Boat> boats = new HashMap<>();

    class BoatHit {
        Integer hits;
        boolean destroyed;
    }

    public ShootingPhase(
            PlayerId player1,
            PlayerId player2,
            Map<Coordinate, Boat> player1Placements,
            Map<Coordinate, Boat> player2Placements) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Placements = player1Placements;
        this.player2Placements = player2Placements;
        this.currentPlayer = player1;
        extractBoats();
    }

    private void extractBoats() {
        player1Placements.values().stream().map(boat -> boats.put(boat.getName(), boat));
        player2Placements.values().stream().map(boat -> boats.put(boat.getName(), boat));
    }

    public FireResult fire(PlayerId player, Column col, Line line) {
        checkPlayerId(player);
        checkPlayerTurn(player);
        Map<Coordinate, Boat> playerPlacements;
        Map<Coordinate, FireResult> playerShoots;
        Map<Boat, Integer> touchedBoats;
        if (player.equals(player1)) {
            playerPlacements = player2Placements;
            playerShoots = player1Shoots;
            touchedBoats = player1TouchedBoats;
        } else {
            playerPlacements = player1Placements;
            playerShoots = player2Shoots;
            touchedBoats = player2TouchedBoats;
        }
        Boat boat = playerPlacements.get(new Coordinate(col, line));
        FireResult result;
        if (boat == null) {
            result = new FireResult(FireResult.Status.MISS, null);
            currentPlayer = (currentPlayer == player1 ? player2 : player1);
        } else {
            Integer hits = touchedBoats.get(boat);
            if (hits == null) hits = 0;
            hits++;
            if (hits == boat.getLength()) {

            }
            result = new FireResult(FireResult.Status.HIT, boat.getName());
            // TODO : voir si bateau coulé et si partie gagnée
        }
        playerShoots.put(new Coordinate(col, line), result);
        return result;
    }

    public Map<Coordinate, FireResult> getGrid(PlayerId player) {
        checkPlayerId(player);
        if (player.equals(player1)) return player1Shoots;
        else return player1Shoots;
    }

    private void checkPlayerTurn(PlayerId player) throws ShootingPhaseException {
        if (! player.equals(currentPlayer)) throw new ShootingPhaseException("This is the other player turn");
    }

    private void checkPlayerId(PlayerId playerId) throws ShootingPhaseException {
        if (! playerId.equals(player1) && ! playerId.equals(player2)) throw new ShootingPhaseException("Unknown playerId");
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
