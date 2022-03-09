package AlgorithmTestFrame3.config;

import AlgorithmTestFrame3.util.NanoNativeOJUnitTimer;
import AlgorithmTestFrame3.util.OJUnitTimer;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public interface OJUnitConfig {
    //这是数值配置
    default int getWrongCaseOut(){return 3;}
    default int getSecondsTimeOut(){return 10;}
    default boolean getWhetherOutputCorrectCase(){
        return true;
    }

    //这是创建方法
    default PrintWriter getRunnerOutputWriter(){return new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));}
    default OJUnitTimer getOJUnitTimer(){return new NanoNativeOJUnitTimer();}
}
