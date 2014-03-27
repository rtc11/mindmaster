package no.group3.mindmaster.test;

import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;
import junit.framework.TestSuite;

import no.group3.mindmaster.MainActivityTest;
import no.group3.mindmaster.ModelTest;

/**
 * Created by tordly on 21/08/13.
 */
public class Runner extends InstrumentationTestRunner {

    @Override
    public TestSuite getAllTests(){
        InstrumentationTestSuite suite = new InstrumentationTestSuite(this);
        suite.addTestSuite(MainActivityTest.class);
        suite.addTestSuite(ModelTest.class);
        return suite;
    }

    @Override
    public ClassLoader getLoader() {
        return Runner.class.getClassLoader();
    }

}