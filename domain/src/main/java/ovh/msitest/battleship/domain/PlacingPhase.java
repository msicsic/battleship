package ovh.msitest.battleship.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael on 14/09/2015.
 */
public class PlacingPhase {
    private List<Boat> boatsToPlaceOrdered;
    private Game game;

    public PlacingPhase() {
        boatsToPlaceOrdered = new ArrayList<>();
        boatsToPlaceOrdered.add(new Boat("barque", 2));
        boatsToPlaceOrdered.add(new Boat("sous marin", 3));
        boatsToPlaceOrdered.add(new Boat("corvette", 3));
        boatsToPlaceOrdered.add(new Boat("croiseur", 4));
        boatsToPlaceOrdered.add(new Boat("porte avion", 5));
    }

    public PlacingPhase(List<Boat> botsToPlace) {
        this.boatsToPlaceOrdered = botsToPlace;
    }

    public List<Boat> getBoatsToPlaceOrdered() {
        return this.boatsToPlaceOrdered;
    }

    public void place(BoatName boat, Coordinate coordinate, Orientation horizontal) {

    }

    public ShootingPhase finish() {
        return new ShootingPhase();
    }

    public Boat getBoat(BoatName name) {
        return getBoatsToPlaceOrdered().stream()
                .filter(boat -> boat.getName().equals(name)).findFirst()
                .get();
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
