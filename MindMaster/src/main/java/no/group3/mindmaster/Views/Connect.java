package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.Network.Utils;
import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class Connect extends Fragment {
    private Connection con;

    public Connect(Connection con){
        this.con = con;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.connect, container, false);
        Button mainMenuButton = (Button) rootView.findViewById(R.id.buttonmainchal);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext(),con))
                        .commit();
            }
        });
        TextView ipaddress = (TextView) rootView.findViewById(R.id.textIp);
        Utils u = new Utils(getActivity().getBaseContext());
        ipaddress.setText("Your IP-Address is: "+u.getNetworkInfo().get(Connection.IP_ADDRESSS)+". Give it to your opponent and wait for connection");
        return rootView;
    }
}

