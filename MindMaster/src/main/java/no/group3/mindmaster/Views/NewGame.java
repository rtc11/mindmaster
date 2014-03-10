package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.PrintWriter;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class NewGame extends Fragment {

    private Connection con;

    public NewGame(Connection con){
        this.con = con;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.new_game, container, false);
        Button howToButton = (Button) rootView.findViewById(R.id.buttonmainip);
        Button connectButton = (Button) rootView.findViewById(R.id.buttonconnect);
        Button sendMessageButton = (Button) rootView.findViewById(R.id.sendMessageButton);

        EditText input = (EditText) rootView.findViewById(R.id.editText);
        final String address = input.getText().toString();

        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext(), con))
                        .commit();
            }
        });
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.clientThread(address);
            }
        });
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con.sendMessage("lolBLABLABLA");
            }
        });

        return rootView;
    }
}
