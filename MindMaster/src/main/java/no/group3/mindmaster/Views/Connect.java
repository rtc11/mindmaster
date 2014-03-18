package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;
import no.group3.mindmaster.R;

/**
 * THIS IS THE SERVER (HOST)
 */
public class Connect extends Fragment {
    private Connection con;
    private final String TAG = "MindMaster.Connect";
    private static final int PORT = 13443;

    //This is the game creator
    private boolean isGameCreator = true;

    public Connect(Connection con){
        this.con = con;

        Log.d(TAG, "HOSTING GAME");
        Log.d(TAG, "(Input) Connecting...");

        //Start thread for incoming messages (when we host the game)
        con.serverThread(isGameCreator, PORT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.connect, container, false);

        Button mainMenuButton = (Button) rootView.findViewById(R.id.buttonmainchal);
        TextView ipaddress1 = (TextView) rootView.findViewById(R.id.textIp1);
        TextView ipaddress2 = (TextView) rootView.findViewById(R.id.textIp2);
        TextView ipaddress3 = (TextView) rootView.findViewById(R.id.textIp3);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext(),con))
                        .commit();
            }
        });

        ipaddress1.setText("Your IP-Address is:");
        ipaddress2.setText(con.getIP());
        ipaddress3.setText("Give it to your opponent and wait for connection");

        return rootView;
    }
}

