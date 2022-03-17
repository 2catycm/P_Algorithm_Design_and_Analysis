package lab2;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_BunnyTopiaTest {
    @Test
    void sample2() {
        final int N = 5;
        final int M = 4;
        final int[] villageReward = {9,8,7,5,4};
        int[][] graphMat = {
                {1,1,5},
                {2,3,4},
                {2,3,3},
                {4,5,2}};
        final var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        for (int i = 0; i < M; i++) {
            graph.addEdge(graphMat[i][0], graphMat[i][1], graphMat[i][2]);
        }
        assertEquals(12, ProblemB_BunnyTopia.solve(N, M, villageReward, graph));
    }
    @Test
    void sample1() {
        final int N = 5;
        final int M = 4;
        final int[] villageReward = {2,5,4,9,1};
        int[][] graphMat = {
                {1,2,9},
                {2,3,2},
                {1,3,3},
                {3,4,7}};
        final var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        for (int i = 0; i < M; i++) {
            graph.addEdge(graphMat[i][0], graphMat[i][1], graphMat[i][2]);
        }
        assertEquals(5, ProblemB_BunnyTopia.solve(N, M, villageReward, graph));
    }
//    @Test
//    void solve() {
//        final var sampleText = "5 4\n" +
//                "2 5 4 9 1\n" +
//                "1 2 9\n" +
//                "2 3 2\n" +
//                "1 3 3\n" +
//                "3 4 7\n";
//        final var ojReader = new OJReader(new StringBufferInputStream(sampleText));
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        final var ojWriter = new OJWriter(baos);
//        ProblemB_BunnyTopia.in = ojReader;
//        ProblemB_BunnyTopia.out = ojWriter;
//        ProblemB_BunnyTopia.main(null);
//        final var actual = Integer.parseInt(baos.toString());
//        assertEquals(2, actual);
//    }
}