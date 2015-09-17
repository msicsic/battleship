package ovh.msitest.battleship.domain;

import ovh.msitest.battleship.domain.Coordinate.Column;
import ovh.msitest.battleship.domain.Coordinate.Line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mael on 14/09/2015.
 */
public class PlacingPhase {
    private List<Boat> boatsToPlace;
    private PlayerId player1;
    private PlayerId player2;

    private List<PlacedBoat> player1Placements = new ArrayList<>();
    private List<PlacedBoat> player2Placements = new ArrayList<>();

    /**
     * Create a default PlacingPhase (with default players and boats)
     */
    public PlacingPhase() {
        createDefaultPlayers();
        createDefaultBoats();
    }

    private void createDefaultPlayers() {
        player1 = new PlayerId();
        player2 = new PlayerId();
    }

    private void createDefaultBoats() {
        boatsToPlace = new ArrayList<>();
        boatsToPlace.add(new Boat("barque", 2));
        boatsToPlace.add(new Boat("sous marin", 3));
        boatsToPlace.add(new Boat("corvette", 3));
        boatsToPlace.add(new Boat("croiseur", 4));
        boatsToPlace.add(new Boat("porte avion", 5));
    }

    /**
     * Create PlacingPhase with default boats
     * @param player1 player 1
     * @param player2 player 2
     */
    public PlacingPhase(PlayerId player1, PlayerId player2) {
        this.player1 = player1;
        this.player2 = player2;
        createDefaultBoats();
    }

    /**
     * Create PlacingPhase with default players
     * @param botsToPlace Boats to place on the grid
     */
    public PlacingPhase(List<Boat> botsToPlace) {
        this.boatsToPlace = botsToPlace;
        createDefaultPlayers();
    }

    public PlayerId getPlayer1() {
        return player1;
    }

    public PlayerId getPlayer2() {
        return player2;
    }

    public List<Boat> getBoatsToPlace() {
        return this.boatsToPlace;
    }

    public void place(PlayerId player, BoatName boat, Column col, Line line, Orientation horizontal) throws PlacingPhaseException {
        if (player != player1 && player != player2) throw new PlacingPhaseException("Player is not a registered user");
        if (getBoat(boat) == null) throw new PlacingPhaseException("boat is not in the boats list");
        PlacedBoat placement = new PlacedBoat(getBoat(boat), col, line, horizontal);

        // TODO : compute grid placement and check if out of grid
    }

    public void validate(PlayerId playerId) {
    }

    public Boat getBoat(BoatName name) {
        return getBoatsToPlace().stream()
                .filter(boat -> boat.getName().equals(name)).findFirst()
                .get();
    }

    public ShootingPhase startShootingPhase() {
        // TODO
        return new ShootingPhase(player1Placements, player2Placements);
    }

}
