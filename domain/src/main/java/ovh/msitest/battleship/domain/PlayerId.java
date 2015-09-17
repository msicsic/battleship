package ovh.msitest.battleship.domain;

import java.util.UUID;

/**
 * Created by Mael on 17/09/2015.
 */
public class PlayerId {
    UUID id = UUID.randomUUID();

    public PlayerId() {
    }

    public UUID getId() {
        return id;
    }
}
