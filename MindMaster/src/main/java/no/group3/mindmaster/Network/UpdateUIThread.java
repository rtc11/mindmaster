package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;

/**
 * Created by tordly on 18.03.14.
 */
public class UpdateUIThread implements Runnable {

    private String msg;
    private final String TAG = "MindMaster.UpdateUIThread";
    private Context ctxt;
    private Connection con;

    public UpdateUIThread(String str, Context ctxt, Connection con) {
        this.msg = str;
        this.ctxt = ctxt;
        this.con = con;

    }

    @Override
    public void run() {

        //If the message contains "peg" it means that the solution string has been received.
        if (msg.contains("peg")) {
            String solution = msg.replaceAll("peg", "");
            Log.d(TAG, "Solution received: " + solution);

            //Get the solution from the host
            ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
            Controller controller = Controller.getInstance(ctxt, con);
            seq.setSolution(controller.getColorPegSequence(solution));

            //We are the client and received the solution, tell the controller that we are ready
            Controller.isReady = true;
        }
        //The opponent is still waiting for the solution
        else if (msg.contains("waiting")) {

            //Send the solution to the opponent
            ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
            Controller controller = Controller.getInstance(ctxt, con);
            String solution = controller.getColorPegSequenceString(seq.getSolution());
            con.sendMessage("peg" + solution);
        }
    }
}
