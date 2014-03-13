package no.group3.mindmaster.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSolutionSequence {

    private ColorPegSequence solution;

    /**
     * Creates a random solutionSequence if the caller is the creator of the game, or returns the
     * current solution if not.
     *
     * @param isGameCreator - true if the caller is the creator if the game, false if not.
     */
    public ColorPegSolutionSequence(boolean isGameCreator) {
        // If creator - create new random solution sequence
        if (isGameCreator) {
            Colour values[] = Colour.values();
            ArrayList<ColorPeg> sequence = new ArrayList<ColorPeg>();
            for (Colour c : values ) {
                //get a random element from the colour enum and adds it to the sequence
                //extra space to make it easier for the eyes
                sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));
            }
            this.solution = new ColorPegSequence(sequence);
        }
        /*
         Else - the caller is not the creator of the game, which means that a solution has been
         generated by another player. Until that solution has been received, the solution is null.
        */
        else {
            this.solution = null;
        }
    }

    public ColorPegSequence getSolution() {
        return solution;
    }

    /**
     * Should be called by the client of the game, when a solution is received by the creator.
     *
     * @param solutionSequence The ColorPegSequence received from the creator.
     */
    public void setSolution(ColorPegSequence solutionSequence) {
        this.solution = solutionSequence;
    }

    private HashMap<Colour, Integer> getColoursInSolution(){
        HashMap<Colour, Integer> colours = new HashMap<Colour, Integer>();
        for (ColorPeg peg : solution.getSequence()) {
            colours.put(peg.getColour(), colours.get(peg.getColour()) + 1);
        }
        return colours;
    }

    /**
     *
     * @param guess - the guessed ColorPegSequence
     * @return ArrayList<KeyPeg> containing the answer
     */
    public ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess){

        HashMap<Colour, Integer> colours = getColoursInSolution();
        ArrayList<KeyPeg> answer = new ArrayList<KeyPeg>();

        for (int i = 0; i < 4; i++) {
            ColorPeg peg = guess.getSequence().get(i);
            if (solution.getSequence().get(i).getColour().equals(peg.getColour())){
                answer.add(KeyPeg.BLACK);
                colours.put(peg.getColour(), colours.get(peg.getColour()) - 1);
            }
        }
        for (Colour colour : colours.keySet()) {
            for (int i = 0; i < colours.get(colour); i++) {
                answer.add(KeyPeg.WHITE);
            }
        }
        if(answer.size() == 4){
            return answer;
        }
        else if (answer.size() < 4){
            for (int i = 0; i < 4-answer.size(); i++) {
                answer.add(KeyPeg.NONE);
            }
            return answer;
        }
        else{
            System.err.println("answer too big");
            return null;
        }


    }
}
