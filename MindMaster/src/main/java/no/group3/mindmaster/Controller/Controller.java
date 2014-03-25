package no.group3.mindmaster.Controller;

import android.content.Context;
import android.util.Log;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import no.group3.mindmaster.MainActivity;
import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.Globals;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Model.Model;
import no.group3.mindmaster.Network.Connection;

/**
 * Created by Wschive on 06/03/14.
 */

public class Controller{

    private final String TAG = "MindMaster.Controller";
    private Connection connection;
    private Model model;
    private Context ctxt;
    private ColorPegSolutionSequence solution;
    private static Controller ControllerInstance = null;
    private ArrayList<ColorPegSequence> oldHistory;
    private ArrayList<ColorPegSequence> currentHistory;
    /** If this is the client, we are not ready before we receive the solution from the server */
    public static boolean isReady = false;
    public static boolean isGameCreator = false;

    public Controller(Context ctxt) {
        this.ctxt = ctxt;
        this.model = new Model(ctxt);
        this.connection = Connection.getInstance(ctxt);
        oldHistory = new ArrayList<ColorPegSequence>();
        currentHistory = new ArrayList<ColorPegSequence>();
    }
    public static Controller getInstance(Context ctxt){
        if (ControllerInstance == null) {
            synchronized (Controller.class){
                ControllerInstance = new Controller(ctxt);
            }
        }
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
            String solutionString = solution.getSolution().toString();

            //Send the solution to the opponent (the client)
            sendMessage(solutionString);
        }

        //If this is the client
        if(!isGameCreator){
            //Wait until we receive the solution
            while(!isReady){
                try {
                    Thread.sleep(500);
                    sendMessage("waiting");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //The solution have been received and instantiated
            solution = ColorPegSolutionSequence.getInstance(isGameCreator);
        }

        model.setSolution(solution);

        setMyTurn(isGameCreator);
        MainActivity ma = MainActivity.getInstance();
        ma.startGameFragment();

    }


    /**
     * Creates a new game to play alone. Should be equal to the method newGame without the network part
     */
    public void newSoloGame(){
        solution = ColorPegSolutionSequence.getInstance(true);
        //MainActivity ma = MainActivity.getInstance(); TODO:check if this is redundant or not
        //ma.startGameFragment();
    }

    /**
     * Sends the generated solution to the "client"
     */
    private void sendMessage(String solutionString) {
        connection.sendMessage(solutionString);
    }

    public ArrayList<ColorPegSequence> getOldHistory() {
        return oldHistory;
    }

    public ArrayList<ColorPegSequence> getCurrentHistory() {
        return currentHistory;
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
            if (c == 'b') {
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
            else if(c == 'o'){
                colorSequence.add(new ColorPeg(Colour.ORANGE));
            }
        }
        return new ColorPegSequence(colorSequence);
    }
    public void addSequenceToModel(ColorPegSequence colorPegSequence){
        this.model.addToHistory(colorPegSequence);
    }
    public void addOpponentKeyPegsToModel(ArrayList<KeyPeg> keyPegs){
        model.addOpponentKeyPegs(keyPegs);
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

    public String keyPegsToString(ArrayList<KeyPeg> keyPegs){
        StringBuilder string = new StringBuilder();
        string.append("keypegs");
        for(KeyPeg k : keyPegs){
            switch(k.toInt()){
                case 0 : string.append("0"); break;
                case 1 : string.append("1"); break;
                case 2 : string.append("2"); break;
            }
        }
        return string.toString();
    }
    public void setMyTurn(boolean turn) {
        this.model.setMyTurn(turn);
    }
    public boolean isMyTurn(){
        return this.model.isMyTurn();
    }
    public void changeTurn(){
        if(this.model.isMyTurn()){
            this.model.setMyTurn(false);
        }
        else
            this.model.setMyTurn(true);
    }
}
