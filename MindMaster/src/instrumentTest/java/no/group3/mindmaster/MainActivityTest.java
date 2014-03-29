package no.group3.mindmaster;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.TextView;

/**
 * Created by tordly on 27.03.14.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity main;
    private TextView turnText;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();

        main = getActivity();
        turnText = (TextView) main.findViewById(R.id.yourturntext);
    }

    @MediumTest
    public void testSetTurnTest() {
        assertNotNull("TextView yourturntext threw an exception", turnText);
    }

    @MediumTest
    public void testSetTurnTest2(){
        turnText.setText("Turn text");
        assertEquals(turnText.getText(), "Turn text");
    }
}
