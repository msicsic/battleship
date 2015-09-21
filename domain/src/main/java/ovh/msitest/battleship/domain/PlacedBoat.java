package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 17/09/2015.
 */
public class PlacedBoat {
    Boat boat;
    Coordinate coordinate;
    Orientation orientation;

    public PlacedBoat(Boat boat, Coordinate.Column column, Coordinate.Line line, Orientation orientation) {
        this.boat = boat;
        this.coordinate = new Coordinate(column, line);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlacedBoat)) return false;

        PlacedBoat that = (PlacedBoat) o;

        if (boat != null ? !boat.equals(that.boat) : that.boat != null) return false;
        if (coordinate != null ? !coordinate.equals(that.coordinate) : that.coordinate != null) return false;
        return orientation == that.orientation;

    }

    @Override
    public int hashCode() {
        int result = boat != null ? boat.hashCode() : 0;
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        result = 31 * result + (orientation != null ? orientation.hashCode() : 0);
        return result;
    }
}
