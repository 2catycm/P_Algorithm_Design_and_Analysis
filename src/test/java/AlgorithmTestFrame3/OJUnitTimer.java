package AlgorithmTestFrame3;

public interface OJUnitTimer {
    void start();
    void restart();
    void startOrRestart();

    /**
     *
     * @return time since the timer started in ms。
     * Considering that OJ algorithm programs often runs in 0-10s,
     * we require the return type to be ms in double form.
     *
     * Note1: if the method is called but the timer is not running, it would try to read the last memorized time.
     */
    double getCurrentTimePassed();

    void stop();
}
