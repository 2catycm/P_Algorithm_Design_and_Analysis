package lab4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_ManhattanServersTest {

    @Test
    void solveSample1() {
        var x = new int[]{-5, -2, 1, 2, 3};
        var y = new int[]{1, -2, 3, 2, 3};
        final var solution = ProblemB_ManhattanServers.solve(x, y);
        assertEquals(5, solution);
    }

    @Test
    void getMaxAndMinIndexWithValues() {
        var a = new int[]{5,2,4, 3, 1};
        final var result = ProblemB_ManhattanServers.getMaxAndMinIndexWithValues(a);
        assertArrayEquals(new int[]{0, 4, 5, 1}, result);
    }
}