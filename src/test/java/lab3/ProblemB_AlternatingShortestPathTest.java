package lab3;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import random.JudgeRandom;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_AlternatingShortestPathTest {
    private static final Logger logger = Logger.getLogger("lab3.ProblemB_AlternatingShortestPathTest");

    int[][] digraphData;
    int N, M, p;

    @RepeatedTest(100)
    @Timeout(8)
    void findExceptionWhenLarge() {
        N = JudgeRandom.randomInt(1, (int) 1e5);
        M = JudgeRandom.randomInt(1, (int) 1e5);
        p = JudgeRandom.intRandomPrime();
        M+=N-1;
        logger.info(String.format("N=%d, M=%d, p=%d", N, M, p));
        final var digraph = new EdgeWeightedDirectedGraph(N);
        for (int i = 0; i < M-(N-1); i++) {
            digraph.addEdge(JudgeRandom.randomInt(1, N), JudgeRandom.randomInt(1, N), JudgeRandom.randomInt(1, p - 1));
        }
        for (int i = 1; i <= N - 1; i++) {
            digraph.addEdge(i, i+1, JudgeRandom.randomInt(1, p - 1));
        }
        final var solver = new AlternatingShortestPathSolver(digraph, p);
        logger.fine("" + solver.getSolution());
    }

    @Test
    void sample1() {
        p = 5;
        N = 4;
        M = 5;
        digraphData = new int[][]{
                {1, 2, 2},
                {3, 4, 2},
                {1, 3, 2},
                {2, 4, 2},
                {2, 3, 3},
        };
        final var digraph = new EdgeWeightedDirectedGraph(N);
        for (int i = 0; i < M; i++) {
            digraph.addEdge(digraphData[i][0], digraphData[i][1], digraphData[i][2]);
        }
        final var solver = new AlternatingShortestPathSolver(digraph, p);
        assertEquals(4, solver.getSolution());
    }
}