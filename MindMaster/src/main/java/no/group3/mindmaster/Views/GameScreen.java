package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import android.widget.TextView;
import android.widget.Toast;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.Globals;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Model.Model;
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
    private TextView turnText;
    private ListView listView;

    /**
     * This constructor is called when we start a multiplayergame over the network
     * @param ctxt - current context
     * @param con - connection instance
     */
    public GameScreen(Context ctxt, Connection con) {
        this.context = ctxt;
        controller = Controller.getInstance(ctxt);
        controller.addPropertyChangeListener(this);
    }

    /**
     * This constructor is used when we start a singleplayer game
     * @param ctxt
     */
    public GameScreen(Context ctxt) {
        this.context = ctxt;
        controller = Controller.getInstance(ctxt);
        controller.addPropertyChangeListener(this);
        controller.newSoloGame();
        Model.sologame = true;
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

    /**
     * Sets the text to say your turn or not your turn based on Global.isMyTurn()
     */
    public void changeTurnText(){
        if(Globals.isMyTurn()){
            turnText.setText(R.string.yourTurn);
        }
        else{
            turnText.setText(R.string.notYourTurn);
        }

    }

    // Sets historyAdapter to all of the 4 spinners
    private void addSpinnerAdapters() {
        for (int i = 0; i < 4; i++) {
            Spinner spinner = spinnerList.get(i);
            spinner.setAdapter(new SpinnerAdapter(rootView.getContext(), R.layout.spinner_row));
        }
    }

    /**
     * Method adding the historyListAdapter. Should be called once when this screen is created.
     */
    private void addHistoryAdapter() {
        Log.d(TAG, "Trying to get activity and find view.");

        listView = (ListView)getActivity().findViewById(R.id.listView_history);

        Log.d(TAG, "Trying to create new historyAdapter.");
        historyAdapter = new HistoryViewAdapter(rootView.getContext(), currentHistory);

        Log.d(TAG, "Trying to set the historyAdapter.");
        listView.setAdapter(historyAdapter);
    }

    /**
     * Method calling the notifyDataSetChanged method of the historyListAdapter. Should be called
     * every time there is a change in the history.
     *
     * @param newHistory ArrayList with the colorPegSequence of the new history. This should be the
     *                   entire new history.
     */
    private void notifyHistoryAdapter(ArrayList<ColorPegSequence> newHistory) {
        currentHistory.clear();
        currentHistory.addAll(newHistory);
        Globals.changeTurn();
        changeTurnText();
        lastGuess = currentHistory.get(currentHistory.size() - 1);
        historyAdapter.notifyDataSetChanged();
        listView.setSelection(historyAdapter.getCount() - 1);
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
        turnText = (TextView)rootView.findViewById(R.id.yourTextView);

        if(Globals.isMyTurn()){
            turnText.setText(R.string.yourTurn);
        }
        else{
            turnText.setText(R.string.notYourTurn);
        }
        currentHistory = new ArrayList<ColorPegSequence>();
        addHistoryAdapter();

        Button okButton = (Button) getActivity().findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Globals.isMyTurn()){
                    if(pegsList.size() != 0){
                        turnText.setText(R.string.notYourTurn);
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
                    turnText.setText(R.string.notYourTurn);
                    Globals.changeTurn();
                    pegsList = new ArrayList<ColorPeg>();
                    getFragmentManager().beginTransaction()
                            .addToBackStack(null)
                            .commit();

                }
                else
                    Toast.makeText(rootView.getContext(), "It is not your turn", Toast.LENGTH_LONG);
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
