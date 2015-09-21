package ovh.msitest.battleship.domain;

import javax.validation.constraints.NotNull;

/**
 * Created by Mael on 11/09/2015.
 */
public class Coordinate {
    @NotNull private final Line line;
    @NotNull private final Column column;

    public Coordinate bottom() {
        if (line.ordinal()<9) {
            return new Coordinate(this.column, Line.values()[line.ordinal() + 1]);
        } else {
            return null;
        }
    }

    public Coordinate right() {
        if (column.ordinal()<9) {
            return new Coordinate(Column.values()[column.ordinal() + 1], this.line);
        } else {
            return null;
        }
    }

    public enum Line {
        _1, _2, _3, _4, _5, _6, _7, _8, _9, _10
    }
    public enum Column {
        _A, _B, _C, _D, _E, _F, _G, _H, _I, _J
    }

    public Coordinate(Column c, Line l) {
        this.line = l;
        this.column = c;
    }

    public Line getLine() {
        return line;
    }

    public Column getColumn() {
        return column;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;

        Coordinate that = (Coordinate) o;

        if (line != that.line) return false;
        return column == that.column;

    }

    @Override
    public int hashCode() {
        int result = line != null ? line.hashCode() : 0;
        result = 31 * result + (column != null ? column.hashCode() : 0);
        return result;
    }
}
