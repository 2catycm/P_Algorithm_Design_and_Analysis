package AlgorithmTestFrame3.testcase;

import AlgorithmTestFrame3.NanoNativeOJUnitTimer;
import AlgorithmTestFrame3.OJUnitConfig;
import AlgorithmTestFrame3.OJUnitTimer;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractOJUnitTestCase implements OJUnitTestCase{


    //调用 JUnit 4 的部分流程。
    @Before
    public void setUp() throws Exception {
        ojUnitConfig = this.getOJUnitConfig();
        ojUnitTimer = ojUnitConfig.getOJUnitTimer();
        ojUnitOut = ojUnitConfig.getRunnerOutputWriter();
        ojUnitOut.println("Hi, there! This is OJUnit Test Frame. Now let's begin our journey! ");
    }

    @After
    public void tearDown() throws Exception {

        ojUnitOut.println("\nTest finished. ");
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
        metadata = getListOfMetaData().get(getMetaDataOption());

    }
    @Test
    public void debugSingleCase(){
        ojUnitTimer.startOrRestart();
//        currentInput = getSingleCaseData();
        currentOutput = solve(currentInput);
        final boolean validation = validate();
        if (validation){

        }else{

        }
        System.out.println();
    }


    protected Object currentOutput;
    protected Object currentInput;
    protected Object metadata;

    protected PrintWriter ojUnitOut;
    protected OJUnitConfig ojUnitConfig;
    protected OJUnitTimer ojUnitTimer = new NanoNativeOJUnitTimer();
    protected int casesPassed = 0;
    protected int casesFailed = 0;
}
