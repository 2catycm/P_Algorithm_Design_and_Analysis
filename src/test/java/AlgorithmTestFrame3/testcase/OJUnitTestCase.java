package AlgorithmTestFrame3.testcase;

import AlgorithmTestFrame3.config.OJUnitConfig;
import AlgorithmTestFrame3.result.OJUnitValidation;

import java.util.List;

/**
 * This is a Test frame based on JUnit, you should implement this OJUnitTestCase class to use the frame.
 */
public interface OJUnitTestCase <MetaType, InputType, OutputType>{
    //configuration
    OJUnitConfig getOJUnitConfig();
    //meta data
    int getMetaDataOption();
    List<MetaType> getListOfMetaData();
    //inside loop methods:
    //1.data generation
    InputType getSingleInput();
    InputType getNextInput(MetaType metadata);
    //2.
    OutputType solve(InputType inputInstance);
    OJUnitValidation validate(MetaType metadata, InputType inputInstance, OutputType solutionToBeTested);

    void onCorrectAction(OJUnitConfig config, OJUnitValidation validation);
    void onWrongAction(OJUnitConfig config, OJUnitValidation validation);

    //要不要清理数据呢
}
