package ovh.msitest.battleship.domain;

import java.util.List;

/**
 * Created by Mael on 14/09/2015.
 */
public class ShootingPhase {
    List<PlacedBoat> boats;


    public ShootingPhase(List<PlacedBoat> boats) {
        this.boats = boats;
    }

    public boolean canFire() {
        return false;
    }

    public void fire(Coordinate coordinate) {
        // TODO
    }
}
