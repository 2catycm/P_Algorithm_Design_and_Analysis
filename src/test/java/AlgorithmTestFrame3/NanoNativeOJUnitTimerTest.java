package AlgorithmTestFrame3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class NanoNativeOJUnitTimerTest {
    private OJUnitTimer timer;
    @Before
    public void setUp() throws Exception {
        timer = new NanoNativeOJUnitTimer();
    }

    @After
    public void tearDown() throws Exception {
        timer.stop();
    }

    @Test
    public void startOrRestart() {
        for (int i = 0; i < 100; i++) {
            timer.startOrRestart();
        }
        System.out.println(timer.getCurrentTimePassed());
    }

    @Test
    public void getCurrentTimePassed() {
        final Random random = new Random();
        final double[] doubles = IntStream.range(0, 100000000).mapToDouble(i -> random.nextDouble()).toArray();
        timer.startOrRestart();
        Arrays.sort(doubles);
        System.out.println(timer.getCurrentTimePassed());
    }
}