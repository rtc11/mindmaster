package no.group3.mindmaster.Controller;

import android.content.Context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Model.Model;
import no.group3.mindmaster.Network.Connection;

/**
 * Created by Wschive on 06/03/14.
 */

public class Controller implements PropertyChangeListener{

    private Connection connection;
    private Model model;
    private Context ctxt;
    private ColorPegSolutionSequence solution;
    static private Controller ControllerInstance = null;
    private ArrayList<ColorPegSequence> oldHistory;
    private ArrayList<ColorPegSequence> currentHistory;
    /** If this is the client, we are not ready before we receive the solution from the server */
    public static boolean isReady = false;

    public Controller(Context ctxt, Connection con) {
        this.model = new Model(null);
        this.ctxt = ctxt;
        this.connection = con;
        oldHistory = new ArrayList<ColorPegSequence>();
        currentHistory = new ArrayList<ColorPegSequence>();
    }
    public static Controller instance(Context ctxt, Connection con){
        if (ControllerInstance == null) {
            ControllerInstance = new Controller(ctxt, con);
        }
        return ControllerInstance;
    }

    public static Controller getControllerInstance(){
        return ControllerInstance;
    }

    /**
     * Method that should be called whenever a new game is created. This should be called once the
     * game is operational, and both players are ready to receive messages.
     *
     * @param isGameCreator True if the player is creating the game, i.e. entering the ip address
     *                      of the player he wants to connect to. False if someone is connecting to
     *                      this player.
     */
    public void newGame(boolean isGameCreator) {

        //If this is the game-creator (the host)
        if (isGameCreator) {

            //Create or get the singleton instance of ColorPegSolutionSequence
            solution = ColorPegSolutionSequence.getInstance(isGameCreator);

            //Get the string-representation of the solution
            String solutionString = getColorPegSequenceString(solution.getSolution());

            //Send the solution to the opponent (the client)
            sendSolution(solutionString);
        }

        //If this is the client
        if(!isGameCreator){

            //Wait until we receive the solution
            while(!isReady){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //The solution have been received and instantiated
            solution = ColorPegSolutionSequence.getInstance(isGameCreator);
        }
    }

    /**
     * Creates a new game to play alone. Should be equal to the method newGame without the network part
     */
    public void newSoloGame(){
        solution = ColorPegSolutionSequence.getInstance(true);
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

    /**
     * Takes a String of letters and converts it to a ColorPegSequence
     *
     * @param solution The String of colors in the solution. Must only contain the first letter of
     *                 the color.
     * @return A ColorPegSequence with all the colors in the solution.
     */
    public static ColorPegSequence getColorPegSequence(String solution) {
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
    public void addSequenceToModel(ColorPegSequence colorPegSequence){
        this.model.addToHistory(colorPegSequence);
    }

    /**
     * Method getting the keyPegs for this guess.
     *
     * @param guess the current guess
     * @return ArrayList containing the KeyPegs for the current guess
     */
    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess) {
        return solution.getKeyPegs(guess);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.model.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.model.removePropertyChangeListener(listener);
    }

}
