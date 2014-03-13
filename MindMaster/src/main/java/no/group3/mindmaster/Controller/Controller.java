package no.group3.mindmaster.Controller;

import android.content.Context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.Model;
import no.group3.mindmaster.Network.Connection;

/**
 * Created by Wschive on 06/03/14.
 */

public class Controller implements PropertyChangeListener{

    Connection connection;
    Model model;
    //View view;
    //EventHandler eventHandler;
    Context ctxt;
    /**
     * Boolean variable indicating if this instance of the controller is the creator of the game
     * or if it has accepted an invitation
     */
    boolean isGameCreator;

    private ColorPegSolutionSequence solution;

    private ArrayList<ColorPegSequence> oldHistory;
    private ArrayList<ColorPegSequence> currentHistory;

    public Controller(Context ctxt, boolean isGameCreator) {
        this.ctxt = ctxt;
        connection = new Connection(ctxt);
        this.isGameCreator = isGameCreator;
        oldHistory = new ArrayList<ColorPegSequence>();
        currentHistory = new ArrayList<ColorPegSequence>();

        //Get the solution for this game
        solution = new ColorPegSolutionSequence(isGameCreator);

        //If this player is the creator of the game, he need to send the solution to the other player
        if (isGameCreator) {
            String solutionString = getColorPegSequenceString(solution.getSolution());
            sendSolution(solutionString);
        }
    }

    /**
     * Sends the generated solution to the "client"
     */
    private void sendSolution(String solutionString) {
        connection.sendMessage(solutionString);
    }

    public ArrayList<ColorPegSequence> getOldHistory() {
        return oldHistory;
    }

    public ArrayList<ColorPegSequence> getCurrentHistory() {
        return currentHistory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent pcs){
        //Name of the property that has changed.
        String changedProperty = pcs.getPropertyName();
        oldHistory = (ArrayList<ColorPegSequence>) pcs.getOldValue();
        currentHistory = (ArrayList<ColorPegSequence>) pcs.getNewValue();
    }

    /**
     * Takes a ColorPegSequence and returns a String starting with "peg" followed by the first
     * letter of the four pegs.
     *
     * @param sequence the sequence to analyze.
     * @return The String with the first letters
     */
    public String getColorPegSequenceString (ColorPegSequence sequence) {
        ArrayList<ColorPeg> pegSequence = sequence.getSequence();
        String message = "peg";
        for (int i = 0; i < pegSequence.size(); i++) {
            Colour c = pegSequence.get(i).getColour();
            if (c == Colour.BLUE) {
                message += "b";
            }
            else if (c == Colour.CYAN) {
                message += "c";
            }
            else if (c == Colour.GREEN) {
                message += "g";
            }
            else if (c == Colour.MAGENTA) {
                message += "m";
            }
            else if (c == Colour.RED) {
                message += "r";
            }
            else if (c == Colour.YELLOW) {
                message += "y";
            }
        }
        return message;
    }
}
