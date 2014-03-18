package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import java.util.ArrayList;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import no.group3.mindmaster.Model.ColorPeg;
import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.R;

public class GameScreen extends Fragment {
    // TODO: Change object type in ArrayList to the type of the drawn Peg-object
    private String TAG = "MindMaster.GameScreen";
    private ArrayList<ColorPeg> pegsList;
    private ArrayList<Spinner> spinnerList;
    private View rootView;

    private Controller controller;
    public GameScreen() {
        controller = Controller.getControllerInstance();
        controller.newSoloGame();

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

    public View onCreateView (LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.game_screen, container, false);

        placePegsInSpinners();
        Toast.makeText(rootView.getContext(), ""+spinnerList.get(0), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Spinner:" + spinnerList.get(0));
        Button okButton = (Button) rootView.findViewById(R.id.button_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegsList = new ArrayList<ColorPeg>();
                for (int i = 0; i < spinnerList.size(); i++){
                    pegsList.add(makeColorPeg((String)spinnerList.get(i).getSelectedItem()));
                    Toast.makeText(rootView.getContext(), ""+(String)spinnerList.get(i).getSelectedItem(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, (String)spinnerList.get(i).getSelectedItem());
                }
                controller.addSequenceToModel(new ColorPegSequence(pegsList));
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
    private ColorPeg makeColorPeg(String s){
        ColorPeg c = null;
        if(s.equals("Blue")){
        c = new ColorPeg(Colour.BLUE);
        }
        else if(s.equals("Green")){
            c = new ColorPeg(Colour.GREEN);
        }
        else if(s.equals("Yellow")){
            c = new ColorPeg(Colour.YELLOW);
        }
        else if(s.equals("Red")){
            c = new ColorPeg(Colour.RED);
        }
        return c;
    }
}
