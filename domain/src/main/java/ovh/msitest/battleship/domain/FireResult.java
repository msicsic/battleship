package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 17/09/2015.
 */
public class FireResult {

    public enum Status {
        HIT, MISS, SINK, WIN
    }
    BoatName boatName;
    Status status;

    public FireResult(Status status, BoatName boatName) {
        this.status = status;
        this.boatName = boatName;
    }

    public BoatName getBoatName() {
        return boatName;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isHit() {
        return status != Status.MISS;
    }

    public boolean isSunk() {
        return status == Status.SINK;
    }

    public boolean isWin() {
        return status == Status.WIN;
    }
}
