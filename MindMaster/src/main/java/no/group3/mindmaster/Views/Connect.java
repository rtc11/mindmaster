package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.content.Context;
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

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;
import no.group3.mindmaster.R;

/**
 * THIS IS THE SERVER (HOST)
 */
public class Connect extends Fragment {
    private Context ctxt;
    private final String TAG = "MindMaster.Connect";
    private Connection con;
    private Controller controller;

    public Connect(Context ctxt){
        this.ctxt = ctxt;
        Log.d(TAG, "HOSTING GAME");
        con = Connection.getInstance(ctxt);
        controller = Controller.getInstance(ctxt);
        controller.setAsGameCreator(true);

        Log.d(TAG, "Connecting...");
        con.serverThread();
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
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext()))
                        .commit();
            }
        });

        ipaddress1.setText("Your IP-Address is:");
        ipaddress2.setText(con.getIP());
        ipaddress3.setText("Give it to your opponent and wait for connection");

        return rootView;
    }
}

