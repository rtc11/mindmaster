package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import no.group3.mindmaster.Network.Connection;

import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class HowToFragment extends Fragment {

    private Connection con;
    private Context ctxt;

    public HowToFragment(Context ctxt) {
        this.ctxt = ctxt;
        this.con = Connection.getInstance(ctxt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.how_to, container, false);
        Button button_Main = (Button) rootView.findViewById(R.id.buttonmain);
        button_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new MainMenuFragment(getActivity().getBaseContext()))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
}