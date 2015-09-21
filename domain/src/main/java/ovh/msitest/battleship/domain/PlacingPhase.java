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
    List<BoatName> player1PlacedBoats = new ArrayList<>();
    List<BoatName> player2PlacedBoats = new ArrayList<>();
    Map<Coordinate, Boat> player1CellPlacement = new HashMap<>();
    Map<Coordinate, Boat> player2CellPlacement = new HashMap<>();
    List<PlayerId> playerValidationOrder = new ArrayList<>();

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

    public void place(PlayerId player, BoatName boatName, Column col, Line line, Orientation orientation) throws PlacingPhaseException {
        checkPlayerId(player);
        if (getBoat(boatName) == null) throw new PlacingPhaseException("boat is not in the boats list");
        PlacedBoat placement = new PlacedBoat(getBoat(boatName), col, line, orientation);
        List<BoatName> placedBoats;
        List<PlacedBoat> placements;
        if (player.equals(player1)) {
            placedBoats = player1PlacedBoats;
            placements = player1Placements;
        } else {
            placedBoats = player2PlacedBoats;
            placements = player2Placements;
        }
        if (placedBoats.contains(boatName)) throw new PlacingPhaseException("Cannot place same boat multiple times");
        placedBoats.add(boatName);
        placements.add(placement);
        fillGrid(player, getBoat(boatName), col, line, orientation);
    }

    private void fillGrid(PlayerId player, Boat boat, Column col, Line line, Orientation orientation) throws PlacingPhaseException {
        Map<Coordinate, Boat> cellPlacement;
        if (player.equals(player1)) {
            cellPlacement = player1CellPlacement;
        } else {
            cellPlacement = player2CellPlacement;
        }
        Coordinate coord = new Coordinate(col, line);
        fillCell(cellPlacement, coord, boat);
        for (int i=1; i<boat.getLength(); i++) {
            if (orientation == Orientation.HORIZONTAL) {
                coord = coord.right();
            } else {
                coord = coord.bottom();
            }
            fillCell(cellPlacement, coord, boat);
        }
    }

    private void fillCell(Map<Coordinate, Boat> cellPlacement, Coordinate coord, Boat boat) {
        if (coord == null) throw new PlacingPhaseException("Boat cannot fit in grid");
        if (cellPlacement.containsKey(coord)) throw new PlacingPhaseException("Boats cannot overlap");
        cellPlacement.put(coord, boat);
    }

    public void validate(PlayerId playerId) throws PlacingPhaseException {
        checkPlayerId(playerId);
        List<PlacedBoat> playerPlacements;
        if (playerId.equals(player1)) {
            playerPlacements = player1Placements;
        } else {
            playerPlacements = player2Placements;
        }
        if (getBoatsToPlace().size() > playerPlacements.size()) {
            throw new PlacingPhaseException("Player must place all his boats before validate");
        }
        if (! playerValidationOrder.contains(playerId)) playerValidationOrder.add(playerId);
    }

    public Boat getBoat(BoatName name) {
        return getBoatsToPlace().stream()
                .filter(boat -> boat.getName().equals(name)).findFirst()
                .orElse(null);
    }

    public ShootingPhase startShootingPhase() {
        if (playerValidationOrder.size() < 2) {
            throw new PlacingPhaseException("Both players must validate boats placement before Shooting Phase can start");
        }
        PlayerId firstPlayer;
        PlayerId secondPlayer;
        if (playerValidationOrder.get(0).equals(player1)) {
            firstPlayer = player1;
            secondPlayer = player2;
        } else {
            firstPlayer = player2;
            secondPlayer = player1;
        }
        return new ShootingPhase(firstPlayer, secondPlayer, player1CellPlacement, player2CellPlacement);
    }

    private void checkPlayerId(PlayerId player) throws PlacingPhaseException {
        if (! player.equals(player1) && ! player.equals(player2)) throw new PlacingPhaseException("Player is not a registered user");
    }
}
