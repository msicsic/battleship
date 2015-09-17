package ovh.msitest.battleship.domain;

/**
 * Created by Mael on 17/09/2015.
 */
public class GameException extends RuntimeException {
    public GameException(String message) {
        super(message);
    }
}
