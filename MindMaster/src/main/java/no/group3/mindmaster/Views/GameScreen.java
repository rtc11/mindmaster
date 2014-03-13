package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.util.ArrayList;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.R;

public class GameScreen extends Fragment {
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private ArrayList<ColorPeg> pegsList;
    private ArrayList<Spinner> spinnerList;
    private View rootView;

    public GameScreen() {
        placePegsInSpinners();

    }

    private void placePegsInSpinners(){
        initializeSpinners();
        addSpinnersContent();
    }
    private void addSpinnersContent() {
        for(Spinner s : spinnerList){

        }
    }
    private void initializeSpinners(){
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner1));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner2));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner3));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner4));
    }

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.game_screen, container, false);


        return rootView;
    }
}
