package lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProblemA_ChainStoreTest {
    ProblemA_ChainStore solver = new ProblemA_ChainStore();

    @Test
    void sample2() {
        final int N = 4;
        final int M = 7;
        final int W = 2;
        final int H = 2;
        final int[] openDays = {4,4,4,2};
        final int[] openStores = {1,3,2,1,3,3,1};
        assertFalse(solver.solve(N, M, W, H, openDays, openStores));
    }
    @Test
    void sample1() {
        final int N = 4;
        final int M = 9;
        final int W = 2;
        final int H = 1;
        final int[] openDays = {4,4,6,2};
        final int[] openStores = {1,3,2,1,2,1,1,3,2};
        assertTrue(solver.solve(N, M, W, H, openDays, openStores));
    }
}