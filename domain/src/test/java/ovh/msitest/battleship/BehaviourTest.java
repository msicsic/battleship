package ovh.msitest.battleship;

import org.junit.Test;
import ovh.msitest.battleship.domain.*;

import java.util.*;

import static org.junit.Assert.*;
import static ovh.msitest.battleship.domain.Coordinate.Column.*;
import static ovh.msitest.battleship.domain.Coordinate.Line.*;
import static ovh.msitest.battleship.domain.Orientation.HORIZONTAL;

/**
 * Regles :
 * Le plateau de jeux est un carré de 10x10 cases (A-J en colonnes, 1-10 en ligne)
 * Le jeu est découpé en 2 phases :
 * => phase de placement des bateaux
 * - precond : les 2 joueurs doivent avoir joint la partie
 * - déroulement : chaque joueur place ses 5 bateaux ou il le souhaite, mais sans qu'ils se supperposent ou sortent de la grille
 * Ils jouent independemment (pas chacun leur tour)
 * - fin : les 2 joueurs ont placés tous leurs bateaux et ont déclaré qu'ils sont prets (tous les bateaux doivent etre placés pour
 * pouvoir déclarer etre pret)
 * => phase de tirs
 * - precond : les 2 joueurs ont déclaré etre pret
 * - action :
 * - le premier joueur qui a déclaré etre pret commence en premier
 * - le joueur dont c'est le tour indique une coordonnée de tir, sous la forme [colonne, ligne]
 * - le jeu indique au joueur s'il a : raté, touché (et quel bateau), touché coulé (et quel bateau)
 * - si le coup touche, le meme joueur continue
 * - si le coup manque, le joueur passe le tour à l'autre joueur
 * - fin : lorsqu'un joueur a tous ses bateaux coulés, il a perdu
 */
public class BehaviourTest {


    @Test(expected = GameException.class)
    public void placing_phase_cannot_start_if_both_players_have_not_joined_the_game() {
        // Given (new game, and only 1 player joined the game)
        Game game = new Game();
        game.join();

        // When (player1 try to start PlacingPhase)
        game.startPlacingPhase();

        // Then -> Exception
    }

    @Test
    public void placing_phase_can_start_when_both_players_joined() {
        // Given (new game, and only 1 player joined the game)
        Game game = new Game();
        PlayerId playerId1 = game.join();
        PlayerId playerId2 = game.join();

        // When (player1 try to start PlacingPhase)
        PlacingPhase placingPhase = game.startPlacingPhase();

        // Then (placingPhase contains the 2 players)
        assertNotNull(placingPhase);
        assertEquals(placingPhase.getPlayer1(), playerId1);
        assertEquals(placingPhase.getPlayer2(), playerId2);
    }

    @Test(expected = PlacingPhaseException.class)
    public void a_player_can_place_a_boat_only_once() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When (place one boat 2 times)
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _C, _1, HORIZONTAL);

        // Then -> Exception
    }

    @Test(expected = PlacingPhaseException.class)
    public void a_player_cannot_place_a_boat_that_is_not_defined_in_the_rules() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When (place one boat)
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("bateau inexistant"), _A, _1, HORIZONTAL);

        // Then -> Exception
    }

    @Test(expected = PlacingPhaseException.class)
    public void a_player_cannot_place_boats_that_are_overlapping() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When (place 2 overlapping boats)
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("barque"), _A, _1, HORIZONTAL);

        // Then -> Exception
    }

    @Test(expected = PlacingPhaseException.class)
    public void only_registered_players_can_play() {
        // Given (placingphase with registered players)
        PlayerId registeredPlayer1 = new PlayerId();
        PlayerId registeredPlayer2 = new PlayerId();
        PlayerId unregisteredPlayer = new PlayerId();
        PlacingPhase placingPhase = new PlacingPhase(registeredPlayer1, registeredPlayer2);

        // When (non registered tries to play)
        placingPhase.place(unregisteredPlayer, new BoatName("corvette"), _A, _1, HORIZONTAL);

        // Then -> exception)
    }

    @Test(expected = PlacingPhaseException.class)
    public void a_player_cannot_place_boats_that_are_not_entirely_inside_the_grid() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When (place one boat)
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _I, _10, HORIZONTAL);

        // Then -> Exception
    }

    @Test
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

    @Test(expected = PlacingPhaseException.class)
    public void a_player_cannot_validate_placement_before_all_of_his_boats_are_placed() {
        // Given
        PlacingPhase placingPhase = new PlacingPhase();

        // When
        placingPhase.validate(placingPhase.getPlayer1());

        // Then -> Exception
    }

    @Test(expected = PlacingPhaseException.class)
    public void a_player_cannot_play_again_after_validate() {
        // Given
        List<Boat> boatsToPlace = new ArrayList<>();
        boatsToPlace.add(new Boat("corvette", 3));
        boatsToPlace.add(new Boat("sous marin", 3));
        PlacingPhase placingPhase = new PlacingPhase(boatsToPlace);
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.validate(placingPhase.getPlayer1());

        // When
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("sous marin"), _A, _2, HORIZONTAL);

        // Then -> Exception
    }

    @Test
    public void a_player_can_validate_placement_when_all_boats_are_placed() {
        // Given (PlacingPhase with only one boat to place)
        PlacingPhase placingPhase = new PlacingPhase(Collections.singletonList(new Boat("corvette", 3)));

        // When (player places his last boat)
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);

        // Then (validate does not raise an exception)
        placingPhase.validate(placingPhase.getPlayer1());
    }

    @Test
    public void shooting_phase_can_starts_when_both_players_validated_their_placement() {
        // Given (PlacingPhase with only one boat to place, and both players placed their ships and validated the placement)
        PlacingPhase placingPhase = new PlacingPhase(Collections.singletonList(new Boat("corvette", 3)));
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.place(placingPhase.getPlayer2(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.validate(placingPhase.getPlayer1());
        placingPhase.validate(placingPhase.getPlayer2());

        // When (both players place their last boat)
        ShootingPhase shootingPhase = placingPhase.startShootingPhase();

        // Then (shootingPhase can start)
        assertNotNull(shootingPhase);
    }

    @Test(expected = PlacingPhaseException.class)
    public void shooting_phase_cannot_start_if_both_players_have_not_validated_their_placement() {
        // Given (PlacingPhase with only one boat to place, and both players placed their ships, and only first player validated placement)
        PlacingPhase placingPhase = new PlacingPhase(Collections.singletonList(new Boat("corvette", 3)));
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.place(placingPhase.getPlayer2(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.validate(placingPhase.getPlayer1());

        // When (both players place their last boat)
        placingPhase.startShootingPhase();

        // Then -> Exception
    }

    @Test
    public void the_first_player_who_validates_his_placements_starts_shooting_first() {
        // Given (PlacingPhase with only one boat to place, and all players boats placed and validated (player2 first))
        PlacingPhase placingPhase = new PlacingPhase(Collections.singletonList(new Boat("corvette", 3)));
        placingPhase.place(placingPhase.getPlayer1(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.place(placingPhase.getPlayer2(), new BoatName("corvette"), _A, _1, HORIZONTAL);
        placingPhase.validate(placingPhase.getPlayer2());
        placingPhase.validate(placingPhase.getPlayer1());

        // When (start the ShootingPhase)
        ShootingPhase shootingPhase = placingPhase.startShootingPhase();

        // Then (player2 turn)
        assertEquals(shootingPhase.getCurrentPlayer(), placingPhase.getPlayer2());
    }

    @Test(expected = ShootingPhaseException.class)
    public void a_player_whom_it_is_not_his_turn_cannot_play() {
        // Given a shootingPhase (by default player1 plays first)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player2 try to play)
        shootingPhase.fire(shootingPhase.getPlayer2(), _A, _1);

        // Then -> Exception
    }

    @Test
    public void same_player_shoots_again_when_he_hits_something() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and hit)
        FireResult result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _1);
        assertTrue(result.isHit());

        // Then (player1 can shoot again)
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _B, _1);
        assertTrue(result.isHit());
    }

    @Test
    public void other_player_shoots_when_previous_player_misses() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and miss)
        FireResult result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _2);
        assertEquals(result.isHit(), false);

        // Then (player2 turn)
        assertEquals(shootingPhase.getCurrentPlayer(), shootingPhase.getPlayer2());
    }

    @Test
    public void player_hit_when_play_on_occupied_cell() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and hit)
        FireResult result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _1);

        // Then (hit the 'corvette')
        assertEquals(result.isHit(), true);
        assertEquals(result.getBoatName(), new BoatName("corvette"));
    }

    @Test
    public void player_miss_when_play_on_empty_cell() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and miss)
        FireResult result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _2);

        // Then (miss)
        assertEquals(result.isHit(), false);
    }

    @Test
    public void player_destroys_ship_when_all_cells_belonging_to_one_ship_have_been_hit() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        Boat boat = new Boat("corvette", 3);
        cellPlacements.put(new Coordinate(_A, _1), boat);
        cellPlacements.put(new Coordinate(_B, _1), boat);
        cellPlacements.put(new Coordinate(_C, _1), boat);
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and hit)
        FireResult result;
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _1);
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _B, _1);
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _C, _1);

        // Then (sink)
        assertEquals(result.isSunk(), true);
        assertEquals(result.getBoatName(), new BoatName("corvette"));
    }

    @Test
    public void player1_shoots_on_player2_grid() {
        // Given (shooting phase with different placement for each player)
        Map<Coordinate, Boat> player1CellPlacements = new HashMap<>();
        player1CellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        player1CellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        player1CellPlacements.put(new Coordinate(_C, _1), new Boat("corvette", 3));
        Map<Coordinate, Boat> player2CellPlacements = new HashMap<>();
        player2CellPlacements.put(new Coordinate(_A, _2), new Boat("corvette", 3));
        player2CellPlacements.put(new Coordinate(_B, _2), new Boat("corvette", 3));
        player2CellPlacements.put(new Coordinate(_C, _2), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, player1CellPlacements, player2CellPlacements);

        // When (player1 shoots)
        FireResult result1 = shootingPhase.fire(player1, _A, _2); // HIT
        FireResult result2 = shootingPhase.fire(player1, _A, _1); // MISS

        // Then (he hit based on the grid of player2)
        assertEquals(result1.getStatus(), FireResult.Status.HIT);
        assertEquals(result2.getStatus(), FireResult.Status.MISS);
    }

    @Test
    public void all_previous_shots_must_be_remembered() {
        // Given (shooting phase with one boat on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_C, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // Given 2 hits and 1 miss
        shootingPhase.fire(shootingPhase.getPlayer1(), _A, _1); // HIT
        shootingPhase.fire(shootingPhase.getPlayer1(), _B, _1); // HIT
        shootingPhase.fire(shootingPhase.getPlayer1(), _D, _1); // MISS

        // When (get state of the grid)
        Map<Coordinate, FireResult> grid = shootingPhase.getGrid(player1);

        // Then
        assertEquals(grid.get(new Coordinate(_A, _1)).getStatus(), FireResult.Status.HIT);
        assertEquals(grid.get(new Coordinate(_B, _1)).getStatus(), FireResult.Status.HIT);
        assertEquals(grid.get(new Coordinate(_C, _1)), null);
        assertEquals(grid.get(new Coordinate(_D, _1)).getStatus(), FireResult.Status.MISS);
    }

    @Test
    public void game_ends_when_one_player_destroys_all_remaining_boats_of_the_other_player() {
        // Given (shooting phase with 2 boats on the upper left corner)
        Map<Coordinate, Boat> cellPlacements = new HashMap<>();
        cellPlacements.put(new Coordinate(_A, _1), new Boat("corvette", 3));
        cellPlacements.put(new Coordinate(_B, _1), new Boat("corvette", 3));
        PlayerId player1 = new PlayerId();
        PlayerId player2 = new PlayerId();
        ShootingPhase shootingPhase = new ShootingPhase(player1, player2, cellPlacements, cellPlacements);

        // When (player1 shoots and hit the only boat)
        FireResult result;
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _A, _1);
        result = shootingPhase.fire(shootingPhase.getPlayer1(), _B, _1);

        // Then (win)
        assertEquals(result.isWin(), true);
    }
}

