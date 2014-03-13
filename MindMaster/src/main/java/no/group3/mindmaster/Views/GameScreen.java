package no.group3.mindmaster.Views;

/**
 * Created by Erik on 3/13/14.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;

import no.group3.mindmaster.R;

public class GameScreen extends Fragment {

    public GameScreen() {
    }


    public View onCreateView (LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.game_screen, container, false);

        Button gameScreenButton = (Button) rootView.findViewById(R.id.testGameScreenButton);
        gameScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext()))
                        .commit();
            }
        });
        return rootView;
    }
}
