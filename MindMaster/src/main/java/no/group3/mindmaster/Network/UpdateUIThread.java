package no.group3.mindmaster.Network;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.KeyPeg;

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
            String solution = seq.getSolution().toString();
            con.sendMessage("peg" + solution);
        }
        else if (msg.contains("keypeg")){
            String keyPegString = msg.replaceAll("keypeg", "");
            ArrayList<KeyPeg> keypegs = new ArrayList<KeyPeg>();

            KeyPeg keyp1 = KeyPeg.valueOf(String.valueOf(keyPegString.charAt(0)));
            KeyPeg keyp2 = KeyPeg.valueOf(String.valueOf(keyPegString.charAt(1)));
            KeyPeg keyp3 = KeyPeg.valueOf(String.valueOf(keyPegString.charAt(2)));
            KeyPeg keyp4 = KeyPeg.valueOf(String.valueOf(keyPegString.charAt(3)));
            keypegs.add(keyp1);
            keypegs.add(keyp2);
            keypegs.add(keyp3);
            keypegs.add(keyp4);

            Controller controller = Controller.getInstance(ctxt, con);
            controller.addOpponentKeyPegsToModel(keypegs);

            Log.d(TAG, "Pegs: " + keyp1 + " " + keyp2 + " " + keyp3 + " " + keyp4);
        }
    }
}
