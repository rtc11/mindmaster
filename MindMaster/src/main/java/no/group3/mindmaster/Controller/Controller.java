package no.group3.mindmaster.Controller;

import android.content.Context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Model;
import no.group3.mindmaster.Network.Connection;

/**
 * Created by Wschive on 06/03/14.
 */

public class Controller implements PropertyChangeListener{

    Connection connection;
    Model model;
    //View view;
    //EventHandler eventHandler;
    Context ctxt;

    private ArrayList<ColorPegSequence> oldHistory;
    private ArrayList<ColorPegSequence> currentHistory;

    public Controller(Context ctxt) {
        this.ctxt = ctxt;
        oldHistory = new ArrayList<ColorPegSequence>();
        currentHistory = new ArrayList<ColorPegSequence>();
    }

    public ArrayList<ColorPegSequence> getOldHistory() {
        return oldHistory;
    }

    public ArrayList<ColorPegSequence> getCurrentHistory() {
        return currentHistory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent pcs){
        //Name of the property that has changed.
        String changedProperty = pcs.getPropertyName();
        oldHistory = (ArrayList<ColorPegSequence>) pcs.getOldValue();
        currentHistory = (ArrayList<ColorPegSequence>) pcs.getNewValue();
    }
}
