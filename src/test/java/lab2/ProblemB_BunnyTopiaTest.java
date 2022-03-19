package lab2;

import lab1.ProblemB_ManyToOneStableMatching;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import random.JudgeRandom;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_BunnyTopiaTest {
    private static int[] Ns = {2, 3, 5, 7, 15, (int) 10000,(int) 2e5, (int) 100, (int) 3}; //村庄数量
    private static int[] Ms = {2, 2, 8, 3, 20, (int) 1000,(int) 2e5, (int) 100, (int) 2}; //边数量
    private static int[] MaxRewards = {2, 2, 8, 3, 20, (int) 2e8 ,(int) 20, (int) 1e9, (int) 1e9}; //边数量
//    private static int option = 1; //1 passed
    private static int option = Ns.length-2; // Ns.length-2 passed
    private static int N = Ns[option];
    private static int M = Ms[option];
    private static int MaxReward = MaxRewards[option];

    private static ObjectOutputStream dataSaver;
    private static ObjectInputStream dataLoader;
    private static String dataPath =
            Path.of("src/test/resources/lab2/problemBInput.dat").toAbsolutePath().toString();

    @AfterAll
    static void afterAll() {
        try {
            if (dataSaver != null)
                dataSaver.close();
            if (dataLoader != null)
                dataLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int[] villageReward;
    private int[][] graphMat;

    void setUpFindBug() {
        villageReward = JudgeRandom.randomIntArray(0, MaxReward, N);
        graphMat = new int[M][3];
        for (int i = 0; i < M; i++) {
            graphMat[i][0] = JudgeRandom.randomInt(1, N);
            graphMat[i][1] = JudgeRandom.randomInt(1, N);
            graphMat[i][2] = JudgeRandom.randomInt(0, MaxReward);
        }
    }

    @RepeatedTest(100)
    void findBug() {
        setUpFindBug();
        withMessageValidate();
    }

    private void withMessageValidate() {
        final var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        for (int i = 0; i < M; i++) {
            graph.addEdge(graphMat[i][0], graphMat[i][1], graphMat[i][2]);
        }
        final int solution = ProblemB_BunnyTopia
                .solve(N, M, villageReward, graph);
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("N,M = %d, %d\n\t", N, M));
        messageBuilder.append("villageReward: \n\t").append(Arrays.toString(villageReward)).append("\n");
        messageBuilder.append("graphMat: \n\t").append(Arrays.deepToString(graphMat)).append("\n");
//        messageBuilder.append("graph: \n\t").append(graph).append("\n");
        messageBuilder.append("With solution: \n\t").append(solution).append("\n");
        validate(solution, messageBuilder.toString());
    }
    void validate(int solution, String message){
        try {
            final var expectedSolution = getExpectedSolution(processBuilderRef);
            final var myCSolution = getExpectedSolution(processBuilderC);
            assertEquals(expectedSolution, solution, message);
            assertEquals(expectedSolution, myCSolution, message);
        }catch (InputMismatchException e){
            System.err.println(message);
            e.printStackTrace();
        }
    }
    final ProcessBuilder processBuilderC = new ProcessBuilder("src/main/java/lab2/BunnyTopia.exe");
    final ProcessBuilder processBuilderRef = new ProcessBuilder("src/test/resources/lab2/ref/B.exe");
    int getExpectedSolution(ProcessBuilder processBuilder){
        final Process process;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        final var inputFromProcess = new Scanner(process.getInputStream());
        final var outputToProcess = new PrintWriter(process.getOutputStream());
        outputToProcess.println(N+" "+M);
        for (int i = 0; i < N; i++) {
            outputToProcess.print(villageReward[i]+" ");
        }
        outputToProcess.println();
        for (int i = 0; i < M; i++) {
            outputToProcess.printf("%d %d %d\n", graphMat[i][0], graphMat[i][1], graphMat[i][2]);
        }
        outputToProcess.flush();
        return inputFromProcess.nextInt();
    }
    @Test
    void sample2() {
        N = 5;
        M = 4;
        villageReward = new int[]{9, 8, 7, 5, 4};
        graphMat = new int[][]{
                {1, 1, 5},
                {2, 3, 4},
                {2, 3, 3},
                {4, 5, 2}};
        final var graph = new EdgeWeightedUndirectedGraph<Integer>(N);
        for (int i = 0; i < M; i++) {
            graph.addEdge(graphMat[i][0], graphMat[i][1], graphMat[i][2]);
        }
        assertEquals(12, ProblemB_BunnyTopia.solve(N, M, villageReward, graph));
    }

    @Test
    void sample1() {
        N = 5;
        M = 4;
        villageReward = new int[]{2, 5, 4, 9, 1};
        graphMat = new int[][]{
                {1, 2, 9},
                {2, 3, 2},
                {1, 3, 3},
                {3, 4, 7}};
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