package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class NewGame extends Fragment {

    private final String TAG = "MindMaster.NewGame";
    private Connection con;
    private String address = "";

    public NewGame(Connection con){
        this.con = con;
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

        Button button_Main = (Button) rootView.findViewById(R.id.buttonmain);
        Button connectButton = (Button) rootView.findViewById(R.id.buttonconnect);
        Button sendMessageButton = (Button) rootView.findViewById(R.id.sendMessageButton);
        Button button_TestScreen = (Button) rootView.findViewById(R.id.button_TestScreen);

        EditText input = (EditText) rootView.findViewById(R.id.editText);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                setAddress(editable.toString());
            }
        });

        button_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext(), con))
                        .addToBackStack(null)
                        .commit();
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Connecting...");
                con.clientThread(getAddress());
            }
        });
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: this message needs to automatically be sent, not by clicking send message
                con.sendMessage("clientip"
                        + MainMenu.utils.getNetworkInfo().get(Connection.IP_ADDRESSS));
            }
        });

        button_TestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new GameScreen())
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
}
