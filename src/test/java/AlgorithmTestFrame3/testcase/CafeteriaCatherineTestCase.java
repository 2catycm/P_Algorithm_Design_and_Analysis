package AlgorithmTestFrame3.testcase;

import AlgorithmTestFrame3.OJUnitConfig;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public abstract class CafeteriaCatherineTestCase extends AbstractOJUnitTestCase{
    //请求用户提供
    public OJUnitConfig getOJUnitConfig(){
        return new OJUnitConfig(){};
    }
    public int getMetaDataOption(){
        return 0;
    }
    public List<Object> getListOfMetaData(){
        return Arrays.asList(1,2,3);
    }
    public Object generateData(){
        return null;
    }

    public abstract Object solve(Object inputInstance);
    public abstract boolean validate();


    public Object getSingleCaseData(){
        return Arrays.asList(1,2,3,4,5);
    }
}
