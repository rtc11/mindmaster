package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
 * THIS IS THE SERVER
 */
public class Connect extends Fragment {
    private Connection con;

    //This is the game creator
    private boolean isGameCreator = true;

    public Connect(Connection con){
        this.con = con;

        //Start the server thread for incoming messages
        con.serverThread(isGameCreator);
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

        Utils utils = Utils.getInstance(getActivity().getBaseContext());  //TODO: unused?

        ipaddress1.setText("Your IP-Address is:");
        ipaddress2.setText(con.getIP());
        ipaddress3.setText("Give it to your opponent and wait for connection");

        return rootView;
    }
}

