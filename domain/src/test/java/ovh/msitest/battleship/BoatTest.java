package ovh.msitest.battleship;

import org.junit.Test;
import ovh.msitest.battleship.domain.Boat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BoatTest {

    @Test
    public void boats_equality() {
        String boatName = "boatName";
        int boatLength = 42;

        Boat boat1 = new Boat(boatName, boatLength);
        Boat boat2 = new Boat(boatName, boatLength);

        assertThat(boat1, equalTo(boat2));
    }


}
