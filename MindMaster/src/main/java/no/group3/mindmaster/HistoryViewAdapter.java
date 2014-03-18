package no.group3.mindmaster;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.Colour;

/**
 * Created by JeppeE on 17/03/14.
 */
public class HistoryViewAdapter extends BaseAdapter {

    private final Context context;
    private ColorPegSequence guess;
    private String TAG = "MindMaster.HistoryViewAdapter";
    LayoutInflater inflater = null;

    public HistoryViewAdapter (Context c, ColorPegSequence sequence) {
        this.guess = sequence;
        this.context = c;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Log.d(TAG, "Entering getView");
        Log.d(TAG, "Position: " + position);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        ArrayList<ImageView> images = new ArrayList<ImageView>();

        images.add((ImageView) rowView.findViewById(R.id.list_image1));
        images.add((ImageView) rowView.findViewById(R.id.list_image2));
        images.add((ImageView) rowView.findViewById(R.id.list_image3));
        images.add((ImageView) rowView.findViewById(R.id.list_image4));
        images.add((ImageView) rowView.findViewById(R.id.list_image5));

        //Iterate over the current guess, and set the colors accordingly.
        for (int i = 0; i < guess.getSequence().size(); i++) {
            if (guess.getSequence().get(i).getColour() == Colour.BLUE) {
                images.get(i).setImageResource(R.drawable.blue);
                Log.d(TAG, "Blue icon set.");
            }
            else if (guess.getSequence().get(i).getColour() == Colour.RED) {
                images.get(i).setImageResource(R.drawable.red);
                Log.d(TAG, "Red Icon set.");
            }
            else if (guess.getSequence().get(i).getColour() == Colour.GREEN) {
                images.get(i).setImageResource(R.drawable.green);
                Log.d(TAG, "Green Icon set.");
            }
            //TODO: This color needs to be changed.
            else if (guess.getSequence().get(i).getColour() == Colour.CYAN) {
                images.get(i).setImageResource(R.drawable.blue);
                Log.d(TAG, "Cyan Icon set.");
            }
            else if (guess.getSequence().get(i).getColour() == Colour.MAGENTA) {
                images.get(i).setImageResource(R.drawable.purple);
                Log.d(TAG, "Magenta Icon set.");
            }
            else if (guess.getSequence().get(i).getColour() == Colour.YELLOW) {
                images.get(i).setImageResource(R.drawable.yellow);
                Log.d(TAG, "Yellow Icon set.");
            }
        }
        Log.d(TAG, "Returning rowView");
        return rowView;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}
