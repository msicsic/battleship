import org.junit.Test;
import ovh.msitest.battleship.domain.Boat;
import ovh.msitest.battleship.domain.BoatPlacementException;
import ovh.msitest.battleship.domain.Game;
import ovh.msitest.battleship.domain.Player;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mael on 11/09/2015.
 */
public class BehaviourTest {

    @Test
    public void list_of_boats_to_place_is_ok() {
        // Given
        Game game = new Game();

        // When
        List<Boat> boats = game.getBoatsToPlace();

        // Then (les bons bateaux sont presents)
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 5).count(), 1);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 4).count(), 1);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 3).count(), 2);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 2).count(), 1);
    }

    @Test(expected = BoatPlacementException.class)
    public void one_player_can_place_one_boat_of_the_list_only_once() {
        // Given
        Game game = new Game();
        Boat boat = game.getBoatsToPlace().get(0);
        Player player = game.getPlayers().get(0);
        Placement placement = new Placement(new Coordinate(0,0), Orientation.HORIZONTAL);

        // When (place one boat)
        game.placeBoat(player, boat, placement);

        // Then (cannot place it again)
        game.placeBoat(player, boat, placement);
    }
}

