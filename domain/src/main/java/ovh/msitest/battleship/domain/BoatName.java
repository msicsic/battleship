package ovh.msitest.battleship.domain;

import javax.validation.constraints.NotNull;

/**
 * Created by Mael on 17/09/2015.
 */
public class BoatName {
    @NotNull String name;

    public BoatName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoatName)) return false;

        BoatName boatName = (BoatName) o;

        return name.equals(boatName.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
