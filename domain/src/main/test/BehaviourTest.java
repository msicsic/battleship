import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.hamcrest.Matchers.*;
import org.hamcrest.CoreMatchers.*;
import ovh.msitest.battleship.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by Mael on 11/09/2015.
 */
public class BehaviourTest {

    @Test
    public void list_of_boats_to_place_is_ok() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When
        List<Boat> boats = placingPhase.getBoatsToPlaceOrdered();

        // Then (les bons bateaux sont presents)
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 5).count(), 1);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 4).count(), 1);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 3).count(), 2);
        assertEquals(boats.stream().filter(boat -> boat.getLength() == 2).count(), 1);
    }

    @Test(expected = BoatPlacementException.class)
    public void one_player_can_place_one_boat_of_the_list_only_once() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Boat boat = placingPhase.getBoatsToPlaceOrdered().get(0);
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);
        Coordinate coord2 = new Coordinate(Coordinate.Line._C, Coordinate.Column._1);

        // When (place one boat)
        placingPhase.place(boat, coord1, Orientation.HORIZONTAL);

        // Then (cannot place it again)
        placingPhase.place(boat, coord2, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void cannot_place_boats_that_are_overlapping() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Boat boat1 = placingPhase.getBoatsToPlaceOrdered().get(0);
        Boat boat2 = placingPhase.getBoatsToPlaceOrdered().get(1);
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When (place one boat)
        placingPhase.place(boat1, coord1, Orientation.HORIZONTAL);

        // Then (cannot place other boat on the same place)
        placingPhase.place(boat2, coord1, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void cannot_place_boats_that_is_partially_or_totally_out_of_grid() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Boat boat1 = placingPhase.getBoatsToPlaceOrdered().get(0);
        Coordinate coord1 = new Coordinate(Coordinate.Line._I, Coordinate.Column._10);

        // When (place one boat)
        placingPhase.place(boat1, coord1, Orientation.HORIZONTAL);
    }

    @Test(expected = PlacementPhaseException.class)
    public void one_player_cannot_validate_placement_before_all_boats_are_placed() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When
        placingPhase.finish();
    }

    @Test
    public void one_player_can_validate_placement_only_when_all_boats_are_placed() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._I, Coordinate.Column._10);

        // When
        for (Boat boat : placingPhase.getBoatsToPlaceOrdered()) {
            placingPhase.place(boat, coord1, Orientation.HORIZONTAL);
            coord1 = coord1.bottom().bottom();
        }

        // Given
        placingPhase.finish();
    }

    public void the_shooting_phase_of_the_first_player_to_join_begins_when_all_players_have_finished_their_placing_phases() {
        List<Boat> boats = new ArrayList<>();
        Boat boat = new Boat(3);
        boats.add(boat);
        Game game = new Game(boats);
        PlacingPhase placingPhase1 = game.join("Pierre");
        PlacingPhase placingPhase2 = game.join("Paul");
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._D, Coordinate.Column._3);

        // When
        placingPhase1.place(boat, placementCoordinate, Orientation.HORIZONTAL);
        placingPhase2.place(boat, placementCoordinate, Orientation.HORIZONTAL);
        ShootingPhase shootingPhase1 = placingPhase1.finish();
        ShootingPhase shootingPhase2 = placingPhase2.finish();

        // Then
        assertEquals(shootingPhase1.canFire(), true);
        assertEquals(shootingPhase2.canFire(), false);
    }

    public void the_first_player_who_finish_placement_shots_first() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = game.getNextPlayer();
        Player player2 = game.getNextPlayer();
        for (Boat boat : game.getBoatsToPlaceOrdered()) {
            game.placeBoat(player1, boat, placementCoordinate, Orientation.HORIZONTAL);
            game.placeBoat(player2, boat, placementCoordinate, Orientation.HORIZONTAL);
            placementCoordinate = placementCoordinate.bottom().bottom();
        }

        assertThat(player1.canPlay(), is(true));
        assertThat(player2.canPlay(), is(false));
    }


    public void same_player_plays_when_he_hit_something() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = placeBoats(game, placementCoordinate);

        // play : hit
        player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._1));
        assertEquals(player1.canPlay(), true);
        assertEquals(player1.canPlay(), false);
    }

    public void other_player_turn_when_he_missed() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = placeBoats(game, placementCoordinate);

        // play : miss
        player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._10));
        assertEquals(player1.canPlay(), false);
        assertEquals(player1.canPlay(), true);

    }

    public void player_hit_when_play_on_occupied_cell() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = placeBoats(game, placementCoordinate);

        // play : hit
        PlayResult playResult = player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._1));
        assertEquals(playResult.hasHit(), true);
    }

    public void player_miss_when_play_on_empty_cell() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = placeBoats(game, placementCoordinate);

        // play : miss
        PlayResult playResult = player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._10));
        assertEquals(playResult.hasHit(), false);
    }

    public void player_destroy_ship_when_all_cells_belonging_to_one_ship_are_hit() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When
        Player player1 = placeBoats(game, placementCoordinate);

        // play : miss
        PlayResult playResult;
        playResult = player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._1));
        assertEquals(playResult.hasHit(), true);
        playResult = player1.play(new Coordinate(Coordinate.Line._A, Coordinate.Column._2));
        assertEquals(playResult.hasHit(), true);
        assertEquals(playResult.hasDestroyed(), true);
    }

    private void placeBoats(Game game, Coordinate startingCoordinate) {
        Player player1 = game.getNextPlayer();
        Player player2 = game.getNextPlayer();
        for (Boat boat : game.getBoatsToPlaceOrdered()) {
            game.placeBoat(player1, boat, startingCoordinate, Orientation.HORIZONTAL);
            game.placeBoat(player2, boat, startingCoordinate, Orientation.HORIZONTAL);
            startingCoordinate = startingCoordinate.bottom().bottom();
        }
    }

    public void game_end_when_one_player_destroyed_all_remaining_boats_of_other_player() {

    }
}

