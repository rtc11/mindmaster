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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;

public class GameScreen extends Fragment  implements PropertyChangeListener{
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private String TAG = "MindMaster.GameScreen";
    private ArrayList<ColorPeg> pegsList;

    // Images for pegs
    int arr_images[] = { R.drawable.blue,
            R.drawable.green, R.drawable.orange,
            R.drawable.purple, R.drawable.red, R.drawable.yellow};

    private ArrayList<Spinner> spinnerList;
    private View rootView;

    private Controller controller;
    private LayoutInflater inflater;

    public GameScreen(Context ctxt, Connection con) {
        controller = Controller.getInstance(ctxt, con);
        controller.newSoloGame();
        controller.addPropertyChangeListener(this);
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

        addAdapters();
    }

    // Sets adapter to all of the 4 spinners
    private void addAdapters() {
        for (int i = 0; i < 4; i++) {
            Spinner spinner = spinnerList.get(i);
            spinner.setAdapter(new SpinnerAdapter(getActivity(), R.layout.spinner_row));
        }
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
        pegsList = new ArrayList<ColorPeg>();
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.game_screen, container, false);
        getActivity().setContentView(R.layout.game_screen);

        placePegsInSpinners();
        Button okButton = (Button) rootView.findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pegsList.size() != 0){
                    return;
                }
                for (int i = 0; i < spinnerList.size(); i++){
                    pegsList.add(makeColorPeg(spinnerList.get(i).getSelectedItemId()));
                }
                ColorPegSequence cps = new ColorPegSequence(pegsList);
                try{
                controller.addSequenceToModel(cps);
                }catch(Exception e){
                    e.printStackTrace();
                }
                pegsList = new ArrayList<ColorPeg>();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .commit();
            }
        });
        Log.d(TAG, "Spinner list created");

        //Testing to see if history adapter is working
        ArrayList<ColorPeg> list = new ArrayList<ColorPeg>();
        list.add(new ColorPeg(Colour.BLUE));
        list.add(new ColorPeg(Colour.YELLOW));
        list.add(new ColorPeg(Colour.RED));
        list.add(new ColorPeg(Colour.GREEN));

        ColorPegSequence sequence = new ColorPegSequence(list);

        Log.d(TAG, "Trying to get activity and find view.");

        ListView listView = (ListView)getActivity().findViewById(R.id.history_list);

        Log.d(TAG, "Trying to create new adapter.");
        HistoryViewAdapter adapter = new HistoryViewAdapter(rootView.getContext(), sequence);

        Log.d(TAG, "Trying to set the adapter.");
        listView.setAdapter(adapter);

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
        System.out.println("jeg kjorer naa");
        Toast.makeText(rootView.getContext(), propertyChangeEvent.getPropertyName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, propertyChangeEvent.getPropertyName());
    }

    public class SpinnerAdapter extends ArrayAdapter<String> {

        public SpinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View row = inflater.inflate(R.layout.spinner_row, parent, false);

            ImageView icon = (ImageView)row.findViewById(R.id.image);
            icon.setImageResource(arr_images[position]);

            return row;
        }

        // Number of elements/rows in the spinner associated with the adapter
        @Override
        public int getCount() {
            return 6;
        }
    }
}
