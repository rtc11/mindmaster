package no.group3.mindmaster.Model;


import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.MainActivity;
import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Utils.AlertDialog;

/**
 * Created by Wschive on 06/03/14.
 */
public class Model {

    private static final String TAG = "MindMaster.Model";
    public static boolean sologame = false; //TODO: should this be static?

    /** List of the current currentHistory of the game */
    private ArrayList<ColorPegSequence> currentHistory;
    /** List of the old currentHistory. Used to send with the firePropertyChange() */
    private ArrayList<ColorPegSequence> oldHistory;

    private ArrayList<KeyPeg> oldOpponentKeyPegs;
    private ArrayList<KeyPeg> currentOpponentKeyPegs;

    private ColorPegSolutionSequence solution = null;
    private PropertyChangeSupport pcs;
    private Context ctxt;
    /**
     * Indicates wheter it is your turn or not
     */
    private boolean myTurn;
    /**
     * Constructor for the model.
     */
    public Model(Context ctxt) {
        this.ctxt = ctxt;
        this.currentHistory = new ArrayList<ColorPegSequence>();
        this.oldHistory = new ArrayList<ColorPegSequence>();
        this.pcs = new PropertyChangeSupport(this);
    }
    public String newGame(boolean isGameCreator){
        this.solution = ColorPegSolutionSequence.getInstance(isGameCreator);
        return solution.getSolution().toString();
    }

    public void setSolution(ColorPegSolutionSequence solution){
        this.solution = solution;
    }

    /**
     * Add new property change listener
     * @param listener - the new listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    /**
     * Remove a property change listener
     * @param listener - the listener to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    /**
     * Method firing a propertyChangeEvent with the old and current Peg Sequence.
     */
    /*private void fireChangePegs() {
        this.pcs.firePropertyChange("Updated pegs", oldOpponentKeyPegs, currentOpponentKeyPegs);
    }*/
    /**
     * Method used to add a ColorPegSequence to the currentHistory. Whenever this method is called, the
     * firePropertyChange should also be called as a change is made in the GUI.
     *
     * @param sequence - The sequence that is to be added to currentHistory.
     */
    public void addToHistory(ColorPegSequence sequence){
        //Update the old history before adding the new sequence
        oldHistory = currentHistory;
        currentHistory.add(sequence);

        Connection con = Connection.getInstance(ctxt);
        Controller controller = Controller.getInstance(ctxt);

        ArrayList<KeyPeg> keypegs = controller.getKeyPegs(sequence);

        if (!keypegs.contains(KeyPeg.WHITE) && !keypegs.contains(KeyPeg.TRANSPARENT)){
            Log.d(TAG, "Game won.");
            AlertDialog ad = new AlertDialog(true);
            MainActivity ma = MainActivity.getInstance();
            ad.show(ma.getFragmentManager(), "end_game");
        }

        if(!sologame){
            Log.d(TAG, "Trying to send keypegs to opponent");
            //Send guess to opponent
            con.sendMessage(controller.keyPegsToString(keypegs));
        }
        else{
            Log.d(TAG, "Should not go here unless single player mode");
        }

        fireChange("History");

    }
    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess){
        return solution.getKeyPegs(guess);
    }


    public void addOpponentKeyPegs(ArrayList<KeyPeg> opponentKeyPegs){
        this.oldOpponentKeyPegs = this.currentOpponentKeyPegs;
        this.currentOpponentKeyPegs = opponentKeyPegs;
        this.setMyTurn(true);
        fireChange("Pegs");
        MainActivity ma = MainActivity.getInstance();
        ma.setTurnText();
    }

    private void fireChange(String type){
        Log.d(TAG, "Event fired: " + type);

        if(type == "History"){
            for(PropertyChangeListener prop: pcs.getPropertyChangeListeners()){
                    prop.propertyChange(new PropertyChangeEvent(this, "History", oldHistory, currentHistory));
            }
        }else if(type == "Pegs"){
            for(PropertyChangeListener prop: pcs.getPropertyChangeListeners()){
                prop.propertyChange(new PropertyChangeEvent(this, "Pegs", oldOpponentKeyPegs, currentOpponentKeyPegs));
            }
        }
    }

    /**
     * Reset the game
     */
    public void reset() {
        Connection con = Connection.getInstance(ctxt);
        Controller controller = Controller.getInstance(ctxt);

        if(controller.isGameCreator()){
            ColorPegSequence solution = ColorPegSolutionSequence
                .getInstance(controller.isGameCreator())
                .generateSolution();

            String solutionString = solution.toString();
            con.sendMessage(solutionString);
        }
        this.currentHistory = new ArrayList<ColorPegSequence>();
        this.oldHistory = new ArrayList<ColorPegSequence>();
    }

    public void setMyTurn(boolean turn) {
        myTurn = turn;
    }

    public boolean isMyTurn(){
        return myTurn;
    }
}
