package ovh.msitest.battleship.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael on 11/09/2015.
 */
public class Game {

    private List<Boat> boats;
    private List<Player> players;
    private int joinedPlayers = 0;

    public Game() {
        init();
    }

    private void init() {
        boats = new ArrayList<>();
        boats.add(new Boat(5));
        boats.add(new Boat(4));
        boats.add(new Boat(3));
        boats.add(new Boat(3));
        boats.add(new Boat(2));

        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player());

        joinedPlayers = 0;
    }


    public List<Boat> getBoatsToPlaceOrdered() {
        return boats;
    }

    public void placeBoat(Player player, Boat boat, Coordinate coord, Orientation orientation) {
        // TODO
    }

    public Player getNextPlayer() {
        return players.get(joinedPlayers++);
    }
}
