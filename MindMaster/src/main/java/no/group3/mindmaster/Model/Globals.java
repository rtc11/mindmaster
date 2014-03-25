package no.group3.mindmaster.Model;

/**
 * Created by Wschive on 13/03/14.
 */
public class Globals {
    /**
     * the length of sequences, indicating how many pegs are used in a guess
     */
    public static int SEQUENCELENGTH = 4;

    /**
     * the number of colours in the game
     */
    public static int NUMBEROFCOLOURS = Colour.values().length;

    /**
     * Indicates wheter it is your turn or not
     */
    private static boolean myTurn = false;

    public static void setMyTurn(boolean turn) {
        myTurn = turn;
    }
    public static boolean isMyTurn(){
        return myTurn;
    }
    public static  void changeTurn(){
        if(myTurn){
            myTurn = false;
        }
        else
            myTurn = true;
    }
}
