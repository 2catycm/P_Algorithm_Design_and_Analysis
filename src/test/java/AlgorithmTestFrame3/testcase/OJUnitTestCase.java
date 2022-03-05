package AlgorithmTestFrame3.testcase;

import AlgorithmTestFrame3.OJUnitConfig;

import java.util.List;

/**
 * This is a Test frame based on JUnit, you should implement this OJUnitTestCase class to use the frame.
 */
public interface OJUnitTestCase {
    //configuration
    OJUnitConfig getOJUnitConfig();
    //meta data
    int getMetaDataOption();
    List<Object> getListOfMetaData();
    //inside loop methods:
    //1.data generation
    Object generateData();
    //
    Object solve(Object inputInstance);
    boolean validate();
}
