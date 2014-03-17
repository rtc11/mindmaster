package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import no.group3.mindmaster.HistoryViewAdapter;
import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.R;

public class GameScreen extends Fragment {
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private String TAG = "MindMaster.GameScreen";

    private ArrayList<ColorPeg> pegsList;
    private ArrayList<KeyPeg> keyPegs;
    private ArrayList<Spinner> spinnerList;
    private View rootView;

    private Controller controller;
    public GameScreen() {
        controller = Controller.getControllerInstance();
        controller.newSoloGame();
    }

    /**
     * Method calculating the keyPegs for this guess. The controller is through this method asked
     * to tell the model (ColorPegSolutionSequence) to calculate the KeyPegs.
     *
     * @param guess the current guess
     * @return ArrayList containing the KeyPegs for the current guess
     */
    private ArrayList<KeyPeg> getKeyPegs(ColorPegSequence guess) {
        return controller.getKeyPegs(guess);
    }

    private void placePegsInSpinners(){
        initializeSpinners();
    }

    private void initializeSpinners(){
        spinnerList = new ArrayList<Spinner>();
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner1));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner2));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner3));
        spinnerList.add((Spinner) rootView.findViewById(R.id.spinner4));
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.game_screen, container, false);
        getActivity().setContentView(R.layout.game_screen);

        placePegsInSpinners();
        Log.d(TAG, "Spinner list created");

        //Testing to see if history adapter is working
        ArrayList<ColorPeg> list = new ArrayList<ColorPeg>();
        list.add(new ColorPeg(Colour.BLUE));
        list.add(new ColorPeg(Colour.CYAN));
        list.add(new ColorPeg(Colour.CYAN));
        list.add(new ColorPeg(Colour.CYAN));

        ColorPegSequence sequence = new ColorPegSequence(list);

        Log.d(TAG, "Trying to get activity and find view.");

        ListView listView = (ListView)getActivity().findViewById(R.id.history_list);

        Log.d(TAG, "Trying to create new adapter.");
        HistoryViewAdapter adapter = new HistoryViewAdapter(rootView.getContext(), sequence);

        Log.d(TAG, "Trying to set the adapter.");
        listView.setAdapter(adapter);

        return rootView;
    }
}
