package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class NewGame extends Fragment {

    public NewGame(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.new_game, container, false);

        Button button_TestScreen = (Button) rootView.findViewById(R.id.button_TestScreen);
        button_TestScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new HowTo())
                        .commit();
            }
        });

        Button howToButton = (Button) rootView.findViewById(R.id.buttonmainip);
        howToButton.setOnClickListener(new View.OnClickListener() {
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
