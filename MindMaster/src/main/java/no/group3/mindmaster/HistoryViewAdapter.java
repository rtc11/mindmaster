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
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Log.d(TAG, "Entering getView");

        View rowView = inflater.inflate(R.layout.list_row, parent, false);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_image1);

        for (int i = 0; i < guess.getSequence().size(); i++) {
            if (guess.getSequence().get(i).getColour() == Colour.BLUE) {
                imageView.setImageResource(R.drawable.icon_twitter);
                Log.d(TAG, "Icon set.");
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
