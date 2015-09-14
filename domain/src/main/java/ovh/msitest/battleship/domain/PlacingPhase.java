package ovh.msitest.battleship.domain;

import java.util.List;

/**
 * Created by Mael on 14/09/2015.
 */
public class PlacingPhase {
    private List<Boat> boatsToPlaceOrdered;

    public List<Boat> getBoatsToPlaceOrdered() {
        return boatsToPlaceOrdered;
    }

    public void place(Boat boat, Coordinate coordinate, Orientation horizontal) {

    }

    public ShootingPhase finish() {
        return new ShootingPhase();
    }
}
