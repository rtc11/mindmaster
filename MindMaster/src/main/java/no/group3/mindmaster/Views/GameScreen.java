package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

import no.group3.mindmaster.HistoryViewAdapter;
import android.widget.Button;
import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.Globals;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;
import no.group3.mindmaster.SpinnerAdapter;

public class GameScreen extends Fragment  implements PropertyChangeListener{
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private String TAG = "MindMaster.GameScreen";
    private ArrayList<ColorPeg> pegsList;

    private ArrayList<Spinner> spinnerList;
    private View rootView;

    private Controller controller;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<ColorPegSequence> currentHistory;
    private ColorPegSequence lastGuess;
    private HistoryViewAdapter historyAdapter;

    public GameScreen(Context ctxt, Connection con) {
        controller = Controller.getInstance(ctxt, con);
        controller.newSoloGame();
        controller.addPropertyChangeListener(this);
        this.context = ctxt;
    }

    public GameScreen(Context ctxt) {
        this.context = ctxt;
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

        addSpinnerAdapters();
    }

    // Sets historyAdapter to all of the 4 spinners
    private void addSpinnerAdapters() {
        for (int i = 0; i < 4; i++) {
            Spinner spinner = spinnerList.get(i);
            spinner.setAdapter(new SpinnerAdapter(rootView.getContext(), R.layout.spinner_row));
        }
    }

    private void addHistoryAdapter() {
        Log.d(TAG, "Trying to get activity and find view.");

        ListView listView = (ListView)getActivity().findViewById(R.id.listView_history);

        Log.d(TAG, "Trying to create new historyAdapter.");
        historyAdapter = new HistoryViewAdapter(rootView.getContext(), currentHistory);

        Log.d(TAG, "Trying to set the historyAdapter.");
        listView.setAdapter(historyAdapter);
//        historyAdapter.notifyDataSetChanged();
    }

    private void notifyHistoryAdapter(ArrayList<ColorPegSequence> newHistory) {
        //TODO: cleanup here if this works
        currentHistory.clear();
        currentHistory.addAll(newHistory);

        lastGuess = currentHistory.get(currentHistory.size() - 1);
        historyAdapter.notifyDataSetChanged();
    }

    private void addGuessToHistory() {

    }

    private void initializeSpinners(){
        spinnerList = new ArrayList<Spinner>();
        spinnerList.add((Spinner) getActivity().findViewById(R.id.spinner1));
        spinnerList.add((Spinner) getActivity().findViewById(R.id.spinner2));
        spinnerList.add((Spinner) getActivity().findViewById(R.id.spinner3));
        spinnerList.add((Spinner) getActivity().findViewById(R.id.spinner4));
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pegsList = new ArrayList<ColorPeg>();
        this.inflater = inflater;

        getActivity().setContentView(R.layout.game_screen);

        rootView = inflater.inflate(R.layout.game_screen, container, false);
        placePegsInSpinners();

        currentHistory = new ArrayList<ColorPegSequence>();
        addHistoryAdapter();

        Button okButton = (Button) getActivity().findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Globals.isMyTurn()){
                    if(pegsList.size() != 0){
                        return;
                    }
                    for (int i = 0; i < spinnerList.size(); i++) {
                        pegsList.add(makeColorPeg(spinnerList.get(i).getSelectedItemId()));
                    }
                    ColorPegSequence cps = new ColorPegSequence(pegsList);
                    try {
                        controller.addSequenceToModel(cps);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pegsList = new ArrayList<ColorPeg>();
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .commit();

                }
            }
        });
        //Add the history historyAdapter
        return rootView;
    }
    private ColorPeg makeColorPeg(long l){
        ColorPeg c = null;
        if(l == 0){
            c = new ColorPeg(Colour.BLUE);
        }
        else if(l == 1){
            c = new ColorPeg(Colour.GREEN);
        }
        else if(l == 2){
            c = new ColorPeg(Colour.ORANGE);
        }
        else if(l == 3){
            c = new ColorPeg(Colour.MAGENTA);
        }
        else if(l == 4){
            c = new ColorPeg(Colour.RED);
        }
        else if(l == 5){
            c = new ColorPeg(Colour.YELLOW );
        }
        return c;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        Log.d(TAG, "PropertyChangeEvent with tag: " + propertyChangeEvent.getPropertyName() + " received.");
        ArrayList<ColorPegSequence> history = (ArrayList<ColorPegSequence>) propertyChangeEvent.getNewValue();
        notifyHistoryAdapter(history);
    }
}
