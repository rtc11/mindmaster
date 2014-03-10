package no.group3.mindmaster.Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wschive on 06/03/14.
 */
public class ColorPegSolutionSequence {

    private ColorPegSequence solution;
    /*
     * Creates a random solutionsequence based on the colours in Colour.java
     */
    public ColorPegSolutionSequence(boolean isGameCreator) {
        if (isGameCreator) {
            Colour values[] = Colour.values();
            ArrayList<ColorPeg> sequence = new ArrayList<ColorPeg>();
            for (Colour c : values ) {
                //get a random element from the colour enum and adds it to the sequence
                sequence.add(new ColorPeg( values[(int)(Math.random()*values.length)] ));//extra space to make it easier for the eyes
            }
            this.solution = new ColorPegSequence(sequence);
        }
        else {
            this.solution = getSolution();
        }
    }

    public ColorPegSequence getSolution() {
        return solution;
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
