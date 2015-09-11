package ovh.msitest.battleship.domain;

import javax.validation.constraints.NotNull;

/**
 * Created by Mael on 11/09/2015.
 */
public class Coordinate {
    @NotNull private final Line line;
    @NotNull private final Column column;

    public enum Line {
        _1, _2, _3, _4, _5, _6, _7, _8, _9, _10
    }
    public enum Column {
        _A, _B, _C, _D, _E, _F, _G, _H, _I, _J
    }

    public Coordinate(Line l, Column c) {
        this.line = l;
        this.column = c;
    }

    public Line getLine() {
        return line;
    }

    public Column getColumn() {
        return column;
    }

}