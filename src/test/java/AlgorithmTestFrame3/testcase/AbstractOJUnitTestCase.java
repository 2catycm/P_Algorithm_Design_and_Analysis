package AlgorithmTestFrame3.testcase;

import AlgorithmTestFrame3.result.OJUnitValidation;
import AlgorithmTestFrame3.runner.OJUnitRunner;
import AlgorithmTestFrame3.util.NanoNativeOJUnitTimer;
import AlgorithmTestFrame3.config.OJUnitConfig;
import AlgorithmTestFrame3.util.OJUnitTimer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public abstract class AbstractOJUnitTestCase<MetaType, InputType, OutputType>
        implements OJUnitTestCase<MetaType, InputType, OutputType>,
        OJUnitRunner<MetaType, InputType, OutputType> {
    @Override
    public OJUnitTestCase<MetaType, InputType, OutputType> getTestCase() {
        return this;
    }

    //调用 JUnit 4 的部分流程。
    @BeforeAll
    public void setUp() throws Exception {
        ojUnitConfig = this.getOJUnitConfig();
        ojUnitTimer = ojUnitConfig.getOJUnitTimer();
        ojUnitOut = ojUnitConfig.getRunnerOutputWriter();
        ojUnitOut.println("Hi, there! This is OJUnit Test Frame. Now let's begin our journey! ");
    }

    @AfterAll
    public void tearDown() throws Exception {
        ojUnitOut.println("\n-------------------Test finished. --------------------");
        ojUnitOut.flush();
        ojUnitOut.close();
    }

    /**
     * Don't run this in AbstractOJUnitTestCase.java by JUnit, since the abstract class cannot be instantiated.
     * Usage1 :
     *   1. overrides me and run super.
     *   2. use IntelliJ IDEA and click run on your extended test case class.
     *
     */
    @Test
    public void findBug(){
        ojUnitOut.println("Trying to find bugs of your algorithm: ");
        metadata = getListOfMetaData().get(getMetaDataOption());

    }
    @Test
    public void debugSingleCase(){
        ojUnitOut.println("Testing single case: ");
        ojUnitTimer.startOrRestart();
//        currentInput = getSingleCaseData();
        currentOutput = solve(currentInput);
        final OJUnitValidation validation = validate(metadata, currentInput, currentOutput);
        ojUnitTimer.pauseOrContinue();
        if (validation.getCorrectness()){
            onCorrectAction(ojUnitConfig, validation);
            casesPassed++;
        }else{
            onWrongAction(ojUnitConfig, validation);
            casesFailed++;
        }
        ojUnitOut.printf("Average running time is %.3e ms\n", ojUnitTimer.getCurrentTimePassed());
    }


    protected OutputType currentOutput;
    protected InputType currentInput;
    protected MetaType metadata;

    protected OJUnitConfig ojUnitConfig;
    protected PrintWriter ojUnitOut;
    protected OJUnitTimer ojUnitTimer;
    protected int casesPassed = 0;
    protected int casesFailed = 0;

}
