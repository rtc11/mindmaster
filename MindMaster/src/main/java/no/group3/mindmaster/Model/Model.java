package no.group3.mindmaster.Model;


import android.content.Context;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Network.Connection;

/**
 * Created by Wschive on 06/03/14.
 */
public class Model {
    /** List of the current currentHistory of the game */
    private ArrayList<ColorPegSequence> currentHistory;
    /** List of the old currentHistory. Used to send with the firePropertyChange() */
    private ArrayList<ColorPegSequence> oldHistory;

    private ArrayList<KeyPeg> oldOpponentKeyPegs;
    private ArrayList<KeyPeg> currentOpponentKeyPegs;

    ArrayList<KeyPeg> keyPegs;
    ColorPegSolutionSequence solution = null;
    private PropertyChangeSupport pcs;
    private Context ctxt;

    /**
     * Constructor for the model.
     */
    public Model(Context ctxt) {
        this.ctxt = ctxt;
        this.currentHistory = new ArrayList<ColorPegSequence>();
        this.oldHistory = new ArrayList<ColorPegSequence>();
        pcs = new PropertyChangeSupport(this);
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
        //TODO: Remember to call adapter.notifyDataSetChanged()
        //Update the old history before adding the new sequence
        oldHistory = currentHistory;
        currentHistory.add(sequence);

        Connection con = Connection.getInstance(ctxt);
        Controller controller = Controller.getInstance(ctxt, con);

        ArrayList<KeyPeg> keypegs = controller.getKeyPegs(sequence);

        //Send guess to opponent
        con.sendMessage(controller.keyPegsToString(keypegs));

        fireChange("History");

    }



    public void addOpponentKeyPegs(ArrayList<KeyPeg> opponentKeyPegs){
        this.oldOpponentKeyPegs = this.currentOpponentKeyPegs;
        this.currentOpponentKeyPegs = opponentKeyPegs;
        fireChange("Pegs");
    }

    private void fireChange(String type){
        if(type == "History"){
            for(PropertyChangeListener prop: pcs.getPropertyChangeListeners()){
                    prop.propertyChange(new PropertyChangeEvent(this,"History",oldHistory,currentHistory));
            }
        }else if(type == "Pegs"){
            for(PropertyChangeListener prop: pcs.getPropertyChangeListeners()){
                prop.propertyChange(new PropertyChangeEvent(this,"Pegs",oldOpponentKeyPegs,currentOpponentKeyPegs));
            }
        }
    }
}
