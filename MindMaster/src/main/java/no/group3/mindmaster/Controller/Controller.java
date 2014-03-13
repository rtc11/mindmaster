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
    }

    /**
     * Method that should be called whenever a new game is created. This should be called once the
     * game is operational, and both players are ready to receive messages.
     */
    public void newGame() {
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

    /**
     * Method used to receive the solution from the creator of the game. Needs to be static as it is
     * called from Server.java
     *
     * @param solutionString The solution as a String with all the first letters of the colors in the
     *                 solution
     */
    public static void receiveSolution(String solutionString) {
    }

    public void setSolution(ColorPegSequence receivedSolution) {
        solution.setSolution(receivedSolution);
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

    /**
     * Takes a String of letters and converts it to a ColorPegSequence
     *
     * @param solution The String of colors in the solution. Must only contain the first letter of
     *                 the color.
     * @return A ColorPegSequence with all the colors in the solution.
     */
    public ColorPegSequence getColorPegSequence(String solution) {
        ArrayList<ColorPeg> colorSequence = new ArrayList<ColorPeg>();
        ColorPegSequence sequence;
        for (int i = 0; i < solution.length(); i++) {
            char c = solution.charAt(i);
            if (c == 'c') {
                colorSequence.add(new ColorPeg(Colour.CYAN));
            }
            else if (c == 'b') {
                colorSequence.add(new ColorPeg(Colour.BLUE));
            }
            else if (c == 'g') {
                colorSequence.add(new ColorPeg(Colour.GREEN));
            }
            else if (c == 'm') {
                colorSequence.add(new ColorPeg(Colour.MAGENTA));
            }
            else if (c == 'r') {
                colorSequence.add(new ColorPeg(Colour.RED));
            }
            else if (c == 'y') {
                colorSequence.add(new ColorPeg(Colour.YELLOW));
            }
        }
        return new ColorPegSequence(colorSequence);
    }
}
