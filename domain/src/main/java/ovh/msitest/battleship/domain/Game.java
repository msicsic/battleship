package ovh.msitest.battleship.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mael on 11/09/2015.
 */
public class Game {

    private List<Boat> boats;
    private List<Player> players;

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
    }


    public List<Boat> getBoatsToPlace() {
        return boats;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
