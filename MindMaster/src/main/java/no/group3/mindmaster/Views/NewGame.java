package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.MainActivity;
import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;

/**
 * THIS IS THE CLIENT (JOIN)
 */
public class NewGame extends Fragment {

    private final String TAG = "MindMaster.NewGame";
    private Connection con;
    private String address = "";
    private Context ctxt;
    private Controller controller;
    private static final int PORT = 13443;

    //This is not the game creator
    private boolean isGameCreator = false;

    public NewGame(Context ctxt){
        this.ctxt = ctxt;
        this.con = Connection.getInstance(ctxt);
        this.controller = Controller.getInstance(ctxt);
        this.controller.setAsGameCreator(false);
        Log.d(TAG, "JOINING GAME");
    }

    private String getAddress(){
        return this.address;
    }

    private void setAddress(String address){
        this.address = address;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.new_game, container, false);

        Button button_Main = (Button) rootView.findViewById(R.id.buttonmainip);
        Button connectButton = (Button) rootView.findViewById(R.id.buttonconnect);
        Button button_TestScreen = (Button) rootView.findViewById(R.id.button_TestScreen);

        EditText input = (EditText) rootView.findViewById(R.id.editText);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }
            @Override
            public void afterTextChanged(Editable editable) {
                //Get the IP from the input field
                setAddress(editable.toString());
            }
        });

        //JOIN GAME THAT IS HOSTED ON IP:
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Connecting...");
                con.clientThread(getAddress());
            }
        });

        //BACK TO MAIN MENU
        button_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext()))
                        .addToBackStack(null)
                        .commit();
            }
        });

        //Start single player
        button_TestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new GameScreen(getActivity().getBaseContext(), true))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
}
