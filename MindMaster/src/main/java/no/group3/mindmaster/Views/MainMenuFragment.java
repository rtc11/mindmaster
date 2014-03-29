package no.group3.mindmaster.Views;

/**
 * Created by Petter on 10.03.14.
 */

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;
import no.group3.mindmaster.Network.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainMenuFragment extends Fragment {

    private Utils utils;
    private Connection con;
    private Context ctxt;

    public MainMenuFragment(Context ctxt) {
        this.ctxt = ctxt;
        this.utils = Utils.getInstance(ctxt);
        this.con = Connection.getInstance(ctxt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu, container, false);
        Button howToButton = (Button) rootView.findViewById(R.id.buttonHowTo);
        Button newGameButton = (Button) rootView.findViewById(R.id.buttonNewGame);
        Button connectButton = (Button) rootView.findViewById(R.id.buttonConnect);

        //JOIN GAME
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new JoinGameFragment(ctxt))
                        .addToBackStack(null)
                        .commit();
            }
        });

        //HOST GAME
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new HostGameFragment(ctxt))
                        .addToBackStack(null)
                        .commit();
            }
        });

        //HOW TO
        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new HowToFragment(ctxt))
                        .addToBackStack(null)
                        .commit();
            }
        });






        return rootView;
    }
}
