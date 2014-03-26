package no.group3.mindmaster.Views;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import no.group3.mindmaster.Network.Connection;

import no.group3.mindmaster.R;

/**
 * Created by Petter on 10.03.14.
 */
public class HowTo extends Fragment {

    private Connection con;
    private Context ctxt;

    public HowTo(Context ctxt) {
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
                        .replace(R.id.container, new MainMenu(getActivity().getBaseContext()))
                        .addToBackStack(null)
                        .commit();
            }
        });
        return rootView;
    }
}