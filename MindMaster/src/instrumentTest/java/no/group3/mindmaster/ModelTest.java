package no.group3.mindmaster;

import android.test.InstrumentationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Model;

/**
 * Created by tordly on 27.03.14.
 */
public class ModelTest extends InstrumentationTestCase{

    public ModelTest(){
    }

    private MainActivity ma;
    private Controller controller;
    private ColorPegSolutionSequence solution;

    public void setUp() throws Exception{
        super.setUp();
        ma = MainActivity.getInstance();
        controller = Controller.getInstance(ma.getBaseContext());
        solution = ColorPegSolutionSequence.getInstance(controller.isGameCreator());
    }

    @MediumTest
    public void testSolutionSequence(){
        boolean result = false;

        if(solution.getSolution().getSequence().size() > 0){
            result = true;
        }

        assertFalse("The solution has not yet been created", result);
    }
}
