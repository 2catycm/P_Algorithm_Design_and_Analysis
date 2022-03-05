package AlgorithmTestFrame3;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public interface OJUnitConfig {
    default boolean getWhetherOutputCorrectCase(){
        return true;
    }
    default PrintWriter getRunnerOutputWriter(){return new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));}
    default OJUnitTimer getOJUnitTimer(){return new NanoNativeOJUnitTimer();}
}
