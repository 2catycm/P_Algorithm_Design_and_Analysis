package AlgorithmTestFrame3.runner;

import AlgorithmTestFrame3.testcase.OJUnitTestCase;
import org.junit.jupiter.api.Test;

public interface OJUnitRunner <MetaType, InputType, OutputType>{
    OJUnitTestCase<MetaType, InputType, OutputType> getTestCase();
    @Test
    void debugSingleCase();
    @Test
    void findBug();
}
