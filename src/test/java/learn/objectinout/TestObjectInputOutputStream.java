package learn.objectinout;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

public class TestObjectInputOutputStream {
    private static ObjectOutputStream dataSaver;
    private static ObjectInputStream dataLoader;
    private static String path =
            Path.of("src/main/resources/testData/TestObjectInputOutputStream.dat").toAbsolutePath().toString();
    @BeforeAll
    public static void beforeAll() {
//        try {
//            System.out.println(path);
//
////            dataLoader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
////                    path)));
//        } catch (IOException e) {
//            System.err.println("自动保存错误样例暂时不可用: ");
//            e.printStackTrace();
//        }
    }

    int[][] boyPreference, girlPreference;

    @Test
    public void test() throws IOException, ClassNotFoundException {
        dataSaver = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                path)));
        boyPreference = new int[10][10];
        boyPreference[2][3] = 4;
        girlPreference = new int[10][10];
        girlPreference[3][2] = 4;

        dataSaver.writeObject(boyPreference);
        dataSaver.writeObject(girlPreference);
    }
    @Test
    public void read() throws IOException, ClassNotFoundException {
        dataLoader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(
                    path)));
        boyPreference = (int[][]) dataLoader.readObject();
        girlPreference = (int[][]) dataLoader.readObject();
        System.out.println(Arrays.deepToString(boyPreference));
        System.out.println(Arrays.deepToString(girlPreference));
    }
    @AfterAll
    public static void afterAll() {
        try {
            if (dataSaver != null)
                dataSaver.close();
            if (dataLoader != null)
                dataLoader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
