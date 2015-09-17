package java;

import org.junit.Test;
import ovh.msitest.battleship.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Mael on 11/09/2015.
 */
public class BehaviourTest {


    @Test(expected = BoatPlacementException.class)
    public void a_player_can_place_a_boat_only_once() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);
        Coordinate coord2 = new Coordinate(Coordinate.Line._C, Coordinate.Column._1);

        // When (place one boat)
        placingPhase.place(new BoatName("corvette"), coord1, Orientation.HORIZONTAL);

        // Then (cannot place it again)
        placingPhase.place(new BoatName("corvette"), coord2, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void a_player_cannot_place_a_boat_that_is_not_defined_in_the_rules() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When (place one boat)
        placingPhase.place(new BoatName("bateau inexistant"), coord1, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void a_player_cannot_place_boats_that_are_overlapping() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When (place one boat)
        placingPhase.place(new BoatName("corvette"), coord1, Orientation.HORIZONTAL);

        // Then (cannot place other boat on the same place)
        placingPhase.place(new BoatName("barque"), coord1, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void a_player_cannot_place_boats_that_are_partially_or_totally_out_of_grid() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._I, Coordinate.Column._10);

        // When (place one boat)
        placingPhase.place(new BoatName("corvette"), coord1, Orientation.HORIZONTAL);
    }

    public void boats_are_all_defined_with_proper_size() {
        PlacingPhase phase = new PlacingPhase();

        assertNotNull(phase.getBoat(new BoatName("barque")));
        assertNotNull(phase.getBoat(new BoatName("sous marin")));
        assertNotNull(phase.getBoat(new BoatName("corvette")));
        assertNotNull(phase.getBoat(new BoatName("croiseur")));
        assertNotNull(phase.getBoat(new BoatName("porte avion")));

        assertEquals(phase.getBoat(new BoatName("barque")).getLength(), 2);
        assertEquals(phase.getBoat(new BoatName("sous marin")).getLength(), 3);
        assertEquals(phase.getBoat(new BoatName("corvette")).getLength(), 3);
        assertEquals(phase.getBoat(new BoatName("croiseur")).getLength(), 4);
        assertEquals(phase.getBoat(new BoatName("porte avion")).getLength(), 5);
    }

    @Test(expected = PlacementPhaseException.class)
    public void a_player_cannot_validate_placement_before_all_boats_are_placed() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When
        placingPhase.finish();
    }

    @Test
    public void a_player_can_validate_placement_when_all_boats_are_placed() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();
        Coordinate coord1 = new Coordinate(Coordinate.Line._I, Coordinate.Column._10);

        // When
        for (Boat boat : placingPhase.getBoatsToPlaceOrdered()) {
            placingPhase.place(boat.getName(), coord1, Orientation.HORIZONTAL);
            coord1 = coord1.bottom();
        }

        // Given
        placingPhase.finish();
    }

    public void the_first_player_to_finish_his_placements_starts_shooting_first() {
        // Given (a game with only one boat to place)
        List<Boat> boats = new ArrayList<>();
        Boat boat = new Boat("corvette", 3);
        boats.add(boat);
        PlacingPhase placingPhase1 = new PlacingPhase(boats);
        PlacingPhase placingPhase2 = new PlacingPhase(boats);
        Game game = new Game();
        game.setPlacing
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._D, Coordinate.Column._3);

        // When
        placingPhase1.place(boat.getName(), placementCoordinate, Orientation.HORIZONTAL);
        placingPhase2.place(boat.getName(), placementCoordinate, Orientation.HORIZONTAL);
        ShootingPhase shootingPhase2 = placingPhase2.finish();
        ShootingPhase shootingPhase1 = placingPhase1.finish();

        // Then
        assertEquals(shootingPhase2.canFire(), true);
        assertEquals(shootingPhase1.canFire(), false);
    }

    public void same_player_shoots_again_when_he_hits_something() {
        // Given (shooting phase with one boat on the upper left corner)
        PlacedBoat boat = new PlacedBoat(new Boat("corvette", 3), new Coordinate(Coordinate.Line._A, Coordinate.Column._1), Orientation.HORIZONTAL);
        ShootingPhase shootingPhase = new ShootingPhase(Arrays.asList(boat));

        // When
        shootingPhase.fire(new Coordinate(Coordinate.Line._A, Coordinate.Column._1));

        // play : hit
        assertEquals(shootingPhase.canFire(), true);
    }

    public void other_player_shoots_when_previous_player_misses() {
        // Given (shooting phase with one boat on the upper left corner)
        ShootingPhase shootingPhase1 = new ShootingPhase(new ArrayList<>());
        ShootingPhase shootingPhase2 = new ShootingPhase(new ArrayList<>());

        shoot : shootingphase * shootingphase -> shootingphase * shootingPhase

        // When
        shootingPhase1.fire(new Coordinate(Coordinate.Line._A, Coordinate.Column._1));

        // play : hit
        assertEquals(shootingPhase1.canFire(), false);
        assertEquals(shootingPhase2.canFire(), true);
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

