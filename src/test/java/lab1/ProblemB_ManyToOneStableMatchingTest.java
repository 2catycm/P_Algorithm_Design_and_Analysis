package lab1;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import random.JudgeRandom;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class ProblemB_ManyToOneStableMatchingTest {
    private static int[] Ns = {2, 3, 5, 7, 15, 100}; //学生数量
    private static int[] Ms = {2, 2, 8, 3, 4, 100}; //学院数量
//    private static int option = 1; //1 passed
    private static int option = Ns.length-1; //1 passed
    private static int N = Ns[option];
    private static int M = Ms[option];

    private static ObjectOutputStream dataSaver;
    private static ObjectInputStream dataLoader;
    private static String dataPath =
            Path.of("src/main/resources/testData/lab1/ProblemB_ManyToOneStableMatchingTest.dat").toAbsolutePath().toString();

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

    int[] capacities;
    int[][] studentPreferences;
    int[][] collegePreferences;

    void setUpFindBug() {
        capacities = new int[M];
        for (int i = 0; i < M; i++) {
            capacities[i] = JudgeRandom.randomInt(1, N);
        }
        studentPreferences = new int[N][];
        for (int i = 0; i < N; i++) {
            studentPreferences[i] = JudgeRandom.nextRandomPermutation(M);
            for (int j = 0; j < M; j++) {
                studentPreferences[i][j]+=1; //permutation有个0，得加1
                studentPreferences[i][j] *= 1 - 2 * JudgeRandom.randomInt(0, 1);
            }
        }
        collegePreferences = new int[M][];
        for (int i = 0; i < M; i++) {
            collegePreferences[i] = JudgeRandom.nextRandomPermutation(N);
            for (int j = 0; j < N; j++) {
                collegePreferences[i][j] *= 1 - (-2) * JudgeRandom.randomInt(0, 1);
            }
        }
    }

    private void withMessageValidate() {
        final int[][] solution = ProblemB_ManyToOneStableMatching
                .solve(capacities, studentPreferences, collegePreferences);
        final StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("capacities: \n\t").append(Arrays.toString(capacities));
        messageBuilder.append("studentPreferences: \n\t").append(Arrays.deepToString(studentPreferences)).append("\n");
        messageBuilder.append("collegePreferences: \n\t").append(Arrays.deepToString(collegePreferences)).append("\n");
        messageBuilder.append("With solution: \n\t").append(Arrays.toString(solution)).append("\n");
        assertTrue(validate(solution), messageBuilder.toString());
    }

    @RepeatedTest(10)
    void findBug() {
        setUpFindBug();
        withMessageValidate();
    }

    private boolean validate(int[][] solution) {
        for (int i = 0; i < solution.length; i++) {
            for (var e : solution[i]) {
                if (studentPreferences[e][i] < 0)
                    throw new RuntimeException(
                            String.format("\033[32m" + "Student %d prefers not to go to college %d " +
                                    "than to reserve his/her current admission.", e, i));
                if (collegePreferences[i][e] < 0)
                    throw new RuntimeException(
                            String.format("\033[32m" + "College %d prefers to abandon an enrolled " +
                                    "student %d than to reserve him/her.", i, e));
            }
        }
        BitSet isEnrolled = new BitSet();
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                final int student = solution[i][j]; // now he is in college i.
                if (isEnrolled.get(student))
                    throw new RuntimeException(String.format(
                            "Student %d is the second time to %d. "
                            , student, i));
                isEnrolled.set(student);
                for (int k = i + 1; k < M; k++) { //另一个学院
                    if (studentPreferences[student][k]<=studentPreferences[student][i])
                        continue; //没有吸引力
                    if (solution[k].length <capacities[k])
                        throw new RuntimeException(String.format("College %d is capable to " +
                                "enroll student %d and student %d can increase college %d 's satisfaction. ",
                                k, student, student, k));
                    for (int l = 0; l < solution[k].length; l++) { //学院的学生
                        final int anotherStudent = solution[k][l];
                        if (collegePreferences[k][student]>collegePreferences[k][anotherStudent])
                            throw new RuntimeException(String.format("College %d prefers student %d than another student %d that has been enrolled. "
                            , k, student, k));
                    }
                }
            }
        }
        return true;
    }

}