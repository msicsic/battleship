package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 11/09/2015.
 */
public class Boat {
    private int length;
    private BoatName name;

    public Boat(String name, int length) {
        this.name = new BoatName(name);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public BoatName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boat)) return false;

        Boat boat = (Boat) o;

        if (length != boat.length) return false;
        return !(name != null ? !name.equals(boat.name) : boat.name != null);

    }

    @Override
    public int hashCode() {
        int result = length;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
