package no.group3.mindmaster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import no.group3.mindmaster.Controller.Controller;
import no.group3.mindmaster.Model.ColorPegSequence;
import no.group3.mindmaster.Model.ColorPegSolutionSequence;
import no.group3.mindmaster.Model.Colour;
import no.group3.mindmaster.Model.KeyPeg;

/**
 * Created by JeppeE on 17/03/14.
 */
public class HistoryViewAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<ColorPegSequence> history = null;
    private String TAG = "MindMaster.HistoryViewAdapter";
    LayoutInflater inflater = null;

    public HistoryViewAdapter (Context c, ArrayList<ColorPegSequence> history) {
        this.history = history;
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

        //Get the solution so we can calculate the keypegs for the current guess.
        ColorPegSolutionSequence solution = ColorPegSolutionSequence.getInstance(Controller.isGameCreator);

        ColorPegSequence guess = history.get(position);

        //Iterate over the current guess, and set the colors accordingly.
        for (int j = 0; j < guess.getSequence().size(); j++) {
            if (guess.getSequence().get(j).getColour() == Colour.BLUE) {
                images.get(j).setImageResource(R.drawable.blue);
            }
            else if (guess.getSequence().get(j).getColour() == Colour.RED) {
                images.get(j).setImageResource(R.drawable.red);
            }
            else if (guess.getSequence().get(j).getColour() == Colour.GREEN) {
                images.get(j).setImageResource(R.drawable.green);
            }
            else if (guess.getSequence().get(j).getColour() == Colour.ORANGE) {
                images.get(j).setImageResource(R.drawable.orange);
            }
            else if (guess.getSequence().get(j).getColour() == Colour.MAGENTA) {
                images.get(j).setImageResource(R.drawable.purple);
            }
            else if (guess.getSequence().get(j).getColour() == Colour.YELLOW) {
                images.get(j).setImageResource(R.drawable.yellow);
            }
        }
        ArrayList<KeyPeg> keyPegs = solution.getKeyPegs(guess);
        ArrayList<ImageView> keyPegImages = new ArrayList<ImageView>();

        keyPegImages.add((ImageView) rowView.findViewById(R.id.keyPegBottomRightHistory));
        keyPegImages.add((ImageView) rowView.findViewById(R.id.keyPegBottomLeftHistory));
        keyPegImages.add((ImageView) rowView.findViewById(R.id.keyPegTopRightHistory));
        keyPegImages.add((ImageView) rowView.findViewById(R.id.keyPegTopLeftHistory));

        Collections.sort(keyPegs);

        for (int i = keyPegs.size() - 1; i >= 0; i--) {
            Log.d(TAG, "Color: " + keyPegs.get(i));
            if (keyPegs.get(i) == KeyPeg.BLACK) {
                keyPegImages.get(i).setImageResource(R.drawable.black_peg);
            }
            else if (keyPegs.get(i) == KeyPeg.WHITE) {
                keyPegImages.get(i).setImageResource(R.drawable.white_peg);
            }
            else if (keyPegs.get(i) == KeyPeg.TRANSPARENT) {
                keyPegImages.get(i).setImageResource(R.drawable.empty_peg);
            }
        }
        return rowView;
    }

    @Override
    public int getCount() {
        return history.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
