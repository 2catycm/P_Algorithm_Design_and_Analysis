package lab1;


import org.junit.jupiter.api.*;
import random.JudgeRandom;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProblemA_StableMatchingTest {
    private static int[] Ns = {2, 3, 5, 7, 15, 100};
    private static int option = Ns.length-1; //1 passed
    private static int N = Ns[option];

    private static ObjectOutputStream dataSaver;
    private static ObjectInputStream dataLoader;
    private static String dataPath =
            Path.of("src/main/resources/testData/ProblemA_StableMatchingTest.dat").toAbsolutePath().toString();

    //    private static boolean saving
    @BeforeAll
    static void beforeAll() {

    }

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

    void setUpFindBug() {
        boyPreference = new int[N][];
        girlPreference = new int[N][];
        for (int i = 0; i < N; i++) {
            boyPreference[i] = JudgeRandom.nextRandomPermutation(N);
            girlPreference[i] = JudgeRandom.nextRandomPermutation(N);
        }

    }

    int[][] boyPreference, girlPreference;

    @RepeatedTest(10)
    void findBug() {
        setUpFindBug();
        withMessageValidate();
    }

    private void withMessageValidate() {
        final int[] solution = ProblemA_StableMatching.solve(boyPreference, girlPreference, N);
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("boyPreference: \n").append(Arrays.deepToString(boyPreference)).append("\n");
        messageBuilder.append("girlPreference: \n").append(Arrays.deepToString(girlPreference)).append("\n");
        messageBuilder.append("With solution: ").append(Arrays.toString(solution)).append("\n");
//        assertTrue(validate(solution), messageBuilder.toString());
        assertTrue(validate(solution, messageBuilder.toString()));
    }

    private boolean validate(int[] solution, String errorMsg) {
        assertEquals(solution.length, N, "N does not match at" + errorMsg);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) { //for every two pairs in solution match
                //pair i and pair j should be stable.
                int boyI = solution[i];
                int boyJ = solution[j];
                //先看girl i 会不会 跳槽
//                assertTrue(girlPreference[i][boyJ]<=girlPreference[i][boyI]
//                        || boyPreference[boyJ][i]<=boyPreference[boyJ][j],
//                        String.format("对于 pair(%d, %d) 和 (%d, %d), 男孩%d愿意和女孩%d私奔\n at data: %s", boyI, i, boyJ, j, boyJ, i, errorMsg));
                if (!(girlPreference[i][boyJ] <= girlPreference[i][boyI]
                        || boyPreference[boyJ][i] <= boyPreference[boyJ][j])) {
                    onWrongAction();
                    throw new RuntimeException(String.format("对于 pair(%d, %d) 和 (%d, %d), 男孩%d愿意和女孩%d私奔\n at data: %s", boyI, i, boyJ, j, boyJ, i, errorMsg));
                }
                //再看girl j
//                assertTrue(girlPreference[j][boyI]<=girlPreference[j][boyJ]
//                        || boyPreference[boyI][j]<=boyPreference[boyI][i],
//                        String.format("对于 pair(%d, %d) 和 (%d, %d), 男孩%d愿意和女孩%d私奔\n at data: %s", boyI, i, boyJ, j, boyI, j, errorMsg));
                if (!(girlPreference[j][boyI] <= girlPreference[j][boyJ]
                        || boyPreference[boyI][j] <= boyPreference[boyI][i])) {
                    onWrongAction();
                    throw new RuntimeException(String.format("对于 pair(%d, %d) 和 (%d, %d), 男孩%d愿意和女孩%d私奔\n at data: %s", boyI, i, boyJ, j, boyI, j, errorMsg));
                }
            }
        }
        return true;
    }

    private void onWrongAction() {
        try {
            dataSaver = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                    dataPath)));
            dataSaver.writeObject(boyPreference);
            dataSaver.writeObject(girlPreference);
        } catch (IOException e) {
            System.err.println("自动保存错误样例暂时不可用: ");
            e.printStackTrace();
        }
    }

    private void setUpSingleTestDebug() {
        try {
            dataLoader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
                    dataPath)));
            boyPreference = (int[][]) dataLoader.readObject();
            girlPreference = (int[][]) dataLoader.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void singleTestDebug() {
        setUpSingleTestDebug();
        withMessageValidate();
    }
}