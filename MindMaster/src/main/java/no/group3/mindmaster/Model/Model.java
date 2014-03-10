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
    }
}
