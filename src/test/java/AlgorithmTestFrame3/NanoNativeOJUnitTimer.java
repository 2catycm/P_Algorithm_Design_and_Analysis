package AlgorithmTestFrame3;

public class NanoNativeOJUnitTimer implements OJUnitTimer{
    @Override
    public void start() {
        if (isRunning)
            throw new RuntimeException("Error: Trying to start a timer that is running！");
        startOrRestart();
    }

    @Override
    public void restart() {
        if (!isRunning)
            throw new RuntimeException("Error: Trying to restart a timer that is not running！");
        startOrRestart();
    }

    @Override
    public void startOrRestart() {
        isRunning = true;
        nanoTimeWhenStarted = System.nanoTime();
    }

    @Override
    public double getCurrentTimePassed() {
        lastStoppedTime = System.nanoTime();
        return (lastStoppedTime-nanoTimeWhenStarted)/1e6;
    }

    @Override
    public void stop() {
        if (!isRunning)
            throw new RuntimeException("Error: Trying to stop a timer that is not running！");
        isRunning = false;
    }

    private long lastStoppedTime = 0;
    private long nanoTimeWhenStarted = 0;
    private boolean isRunning = false;
}
