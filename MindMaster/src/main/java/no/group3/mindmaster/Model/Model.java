package no.group3.mindmaster.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

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
    ColorPegSolutionSequence solution;
    private PropertyChangeSupport pcs;

    /**
     * Constructor for the model.
     *
     * @param solution - The ColorPegSolutionSequence of the current game.
     */
    public Model(ColorPegSolutionSequence solution) {
        this.solution = solution;
        this.currentHistory = new ArrayList<ColorPegSequence>();
        this.oldHistory = new ArrayList<ColorPegSequence>();
        pcs = new PropertyChangeSupport(this);
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
        System.out.println("Historien ble endret");
        this.pcs.firePropertyChange("History",oldHistory,currentHistory);
    }
    public void addOpponentKeyPegs(ArrayList<KeyPeg> opponentKeyPegs){
        this.oldOpponentKeyPegs = this.currentOpponentKeyPegs;
        this.currentOpponentKeyPegs = opponentKeyPegs;
        //fireChange();
    }
}
