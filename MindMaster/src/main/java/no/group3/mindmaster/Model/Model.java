package no.group3.mindmaster.Model;

import android.content.Context;

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

    ArrayList<KeyPeg> keyPegs;
    ColorPegSolutionSequence solution;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructor for the model.
     *
     * @param solution - The ColorPegSolutionSequence of the current game.
     */
    public Model(ColorPegSolutionSequence solution) {
        this.solution = solution;
        this.currentHistory = new ArrayList<ColorPegSequence>();
        this.oldHistory = new ArrayList<ColorPegSequence>();
    }

    //TODO: fix this method
    public void setOpponentKeyPegs(ArrayList<KeyPeg> keypegs){
        ArrayList<KeyPeg> old = this.keyPegs;
        this.keyPegs = keypegs;
        pcs.firePropertyChange("Opponents KeyPegs", old, keyPegs);
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
     * Method firing a propertyChangeEvent with the old and current history.
     */
    private void fireChange() {
        this.pcs.firePropertyChange("Updated history", oldHistory, currentHistory);
    }
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
        fireChange();

        //TODO: get the right context
//        Connection con = Connection.getInstance(ctxt);
//        Controller controller = Controller.getInstance(ctxt, con);
        //TODO: create a tostring method for keypegs
//        ArrayList<KeyPeg> keypegs = controller.getKeyPegs(sequence);
//        con.sendMessage("keypegs" + keypegs.toString());

    }
}
