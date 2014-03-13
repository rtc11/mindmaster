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
import android.widget.TextView;

import no.group3.mindmaster.Network.Connection;
import no.group3.mindmaster.R;
import no.group3.mindmaster.Network.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainMenu extends Fragment {
    public static  Utils utils; //TODO: if context somehow is changes, remember to update it
    private Connection con;

    public MainMenu(Context context, Connection con) {
        this.utils = new Utils(context);
        this.con = con;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu, container, false);
        Button howToButton = (Button) rootView.findViewById(R.id.buttonHowTo);
        Button newGameButton = (Button) rootView.findViewById(R.id.buttonNewGame);
        Button viewChallengesButton = (Button) rootView.findViewById(R.id.buttonConnect);
        howToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new HowTo(con))
                        .addToBackStack(null)
                        .commit();
            }
        });
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new NewGame(con))
                        .addToBackStack(null)
                        .commit();
            }
        });
        viewChallengesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new Connect(con))
                        .commit();
            }
        });


        return rootView;
    }
}
