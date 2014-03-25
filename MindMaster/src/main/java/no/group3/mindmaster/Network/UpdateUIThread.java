package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Globals;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Views.GameScreen;

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

        //If we receive the opponents key pegs combination
        if (msg.contains("keypegs")){
            String keyPegString = msg.replaceAll("keypegs", "");

            ArrayList<KeyPeg> keypegs = new ArrayList<KeyPeg>();
            keypegs.add(KeyPeg.getKeyPeg(Character.getNumericValue(keyPegString.charAt(0))));
            keypegs.add(KeyPeg.getKeyPeg(Character.getNumericValue(keyPegString.charAt(1))));
            keypegs.add(KeyPeg.getKeyPeg(Character.getNumericValue(keyPegString.charAt(2))));
            keypegs.add(KeyPeg.getKeyPeg(Character.getNumericValue(keyPegString.charAt(3))));

            if(!keypegs.contains(KeyPeg.WHITE) && !keypegs.contains(KeyPeg.TRANSPARENT)){
                Log.d(TAG, "you lost");
            }

            Controller controller = Controller.getInstance(ctxt);
            controller.addOpponentKeyPegsToModel(keypegs);
        }
        //If the message contains "peg" it means that the solution string has been received.
        else if (msg.contains("peg")) {
            String solution = msg.replaceAll("peg", "");

            //Get the solution from the host
            ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
            Controller controller = Controller.getInstance(ctxt);
            seq.setSolution(controller.getColorPegSequence(solution));

            //We are the client and received the solution, tell the controller that we are ready
            Controller.isReady = true;
        }

        //The opponent is still waiting for the solution
        else if (msg.contains("waiting")) {
            //Send the solution to the opponent
            ColorPegSolutionSequence seq = ColorPegSolutionSequence.getInstance(false);
            String solution = seq.getSolution().toString();
            con.sendMessage("peg" + solution);
        }
    }
}
