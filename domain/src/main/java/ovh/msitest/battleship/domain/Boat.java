package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 11/09/2015.
 */
public class Boat {
    private int length;
    private BoatName name;

    public Boat(BoatName name, int length) {
        this.length = length;
        this.name = name;
    }

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

}
