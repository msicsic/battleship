import org.junit.Test;
import ovh.msitest.battleship.domain.*;

import java.util.List;

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
        Game game = new Game();

        // When
        List<Boat> boats = game.getBoatsToPlaceOrdered();

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
        Boat boat = game.getBoatsToPlaceOrdered().get(0);
        Player player = game.getNextPlayer();

        // When (place one boat)
        game.placeBoat(player, boat, new Coordinate(Coordinate.Line._A, Coordinate.Column._1), Orientation.HORIZONTAL);

        // Then (cannot place it again)
        game.placeBoat(player, boat, new Coordinate(Coordinate.Line._C, Coordinate.Column._1), Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void cannot_place_boats_that_are_overlapping() {
        // Given
        Game game = new Game();
        Boat boat1 = game.getBoatsToPlaceOrdered().get(0);
        Boat boat2 = game.getBoatsToPlaceOrdered().get(1);
        Player player = game.getNextPlayer();
        Coordinate coord = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);

        // When (place one boat)
        game.placeBoat(player, boat1, coord, Orientation.HORIZONTAL);

        // Then (cannot place other boat on the same place)
        game.placeBoat(player, boat2, coord, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void cannot_place_boats_that_are_touching_each_other() {
        // Given
        Game game = new Game();
        Boat boat1 = game.getBoatsToPlaceOrdered().get(0);
        Boat boat2 = game.getBoatsToPlaceOrdered().get(1);
        Player player = game.getNextPlayer();
        Coordinate coord1 = new Coordinate(Coordinate.Line._A, Coordinate.Column._1);
        Coordinate coord2 = new Coordinate(Coordinate.Line._B, Coordinate.Column._1);

        // When (place one boat)
        game.placeBoat(player, boat1, coord1, Orientation.HORIZONTAL);

        // Then (cannot place other boat just below)
        game.placeBoat(player, boat2, coord2, Orientation.HORIZONTAL);
    }

    @Test(expected = BoatPlacementException.class)
    public void cannot_place_boats_that_is_partially_or_totally_out_of_grid() {
        // Given
        Game game = new Game();
        Player player1 = game.getNextPlayer();
        Coordinate coordCloseToBorder = new Coordinate(Coordinate.Line._J, Coordinate.Column._10);

        // When (place one boat)
        Boat boat1 = game.getBoatsToPlaceOrdered().stream()
                .filter(boat -> boat.getLength() >= 4).findFirst().get();
        game.placeBoat(player1, boat1, coordCloseToBorder, Orientation.HORIZONTAL);

        // Then : Exception
    }

    public void game_can_

    public void once_all_boats_have_been_placed_players_can_play() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._D, Coordinate.Column._3);

        // When
        Player player1 = game.getNextPlayer();
        Player player2 = game.getNextPlayer();
        for (Boat boat : game.getBoatsToPlaceOrdered()) {
            game.placeBoat(player1, boat, placementCoordinate, Orientation.HORIZONTAL);
            game.placeBoat(player2, boat, placementCoordinate, Orientation.HORIZONTAL);
            placementCoordinate = placementCoordinate.bottom().bottom();
        }

        assertTrue(player1.canPlay());
        assertTrue(player2.canPlay());

        currentTurn = currentTurn.play(mon move);
        if currentturn.player == me
                //

        etat du jeux ( a qui c'est de jouer'

                //
        Player pl1 = game.playerJoin("Fabien");
        Player pl2 = game.playerJoin("Mael");
        // test : game.canStart() (on peut commenceer a placer)
        pl1.placeBoat(...);
        pl1.placeBoat(...);

        // test : game.isReadyToPlay(...)
        Player pl = game.nextPlayerToPlay())
        //pl.play(...) -> plante si pas le tours

        //

        Game game = gameFinder.join(gameId);
        player = game.join("Lucien");

        string ok = "ko";


        Type connection : ipServer -> placementPhase;
        type placementPhase : jouerCoup;

        type toto;
        type name : firstName * lastName;
        type maFunction : typ1 => type2







        player.whenPlacing(function(place) { result = "ok" })
        player.joue(..)
        //for each player.place

        Waiting playing = player.donePlacing();

        //while playingplayer == null
           playingPlaying = waitingPlaying.wait();

        mock.ifappeé(mock.methode1);then(...).else(aaa)
        PlacingPlayer = game;

        PlayingPlayer pl1 = ...;
        Player pl = pl1.play(); // -> soit : WaitingPlayer, soit un PlayingPlayer
        if (pl instanceof PlayingPlayer) ;
        else if (pl instanceof WaitingPlayer)





    }

    public void player_cannot_play_while_other_player_has_not_placed_all_boats() {
        // Given
        Game game = new Game();
        Coordinate placementCoordinate = new Coordinate(Coordinate.Line._D, Coordinate.Column._3);

        // When
        Player player1 = game.getNextPlayer();
        for (Boat boat : game.getBoatsToPlaceOrdered()) {
            game.placeBoat(player1, boat, placementCoordinate, Orientation.HORIZONTAL);
            placementCoordinate = placementCoordinate.bottom().bottom();
        }

        assertEquals(player1.canPlay(), false);
    }

    public void player1_plays_first() {
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

        assertEquals(player1.canPlay(), true);
        assertEquals(player2.canPlay(), false);
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

