package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 17/09/2015.
 */
public class PlacedBoat {
    Boat boat;
    Coordinate coordinate;
    Orientation orientation;

    public PlacedBoat(Boat boat, Coordinate coordinate, Orientation orientation) {
        this.boat = boat;
        this.coordinate = coordinate;
        this.orientation = orientation;
    }

    public Boat getBoat() {
        return boat;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
