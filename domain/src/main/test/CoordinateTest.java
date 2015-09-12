import org.junit.Test;
import ovh.msitest.battleship.domain.Coordinate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Mael on 11/09/2015.
 */
public class CoordinateTest {

    @Test
    public void test_that_equals_is_true_for_two_coordinates_with_same_x_and_y() {
        // Given
        Coordinate c1 = new Coordinate(Coordinate.Line._C, Coordinate.Column._1);
        Coordinate c2 = new Coordinate(Coordinate.Line._C, Coordinate.Column._1);

        // Then
        assertEquals(c1, c2);
    }

    @Test
    public void test_that_equals_is_false_for_two_coordinates_with_different_x_and_y() {
        // Given
        Coordinate c1 = new Coordinate(Coordinate.Line._C, Coordinate.Column._1);
        Coordinate c2 = new Coordinate(Coordinate.Line._A, Coordinate.Column._3);

        // Then
        assertFalse(c1.equals(c2));
    }
}
