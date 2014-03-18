package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.util.ArrayList;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.KeyPeg;
import no.group3.mindmaster.R;

public class GameScreen extends Fragment {
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private String TAG = "MindMaster.GameScreen";

    // Images for pegs
    int arr_images[] = { R.drawable.blue,
            R.drawable.green, R.drawable.orange,
            R.drawable.purple, R.drawable.red, R.drawable.yellow};

    private ArrayList<Spinner> spinnerList;
    private View rootView;

    private Controller controller;
    private LayoutInflater inflater;

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
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.game_screen, container, false);
        placePegsInSpinners();
        Log.d(TAG, "Spinner list created");
        return rootView;
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
