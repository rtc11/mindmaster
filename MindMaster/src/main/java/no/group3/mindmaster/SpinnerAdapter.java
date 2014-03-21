package no.group3.mindmaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * Created by JeppeE on 18/03/14.
 */
public class SpinnerAdapter extends ArrayAdapter<String> {

    // Images for pegs
    int arr_images[] = { R.drawable.blue,
            R.drawable.green, R.drawable.orange,
            R.drawable.purple, R.drawable.red, R.drawable.yellow};

    private Context context;
    LayoutInflater inflater = null;

    public SpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View row = inflater.inflate(R.layout.spinner_row, parent, false);

        ImageView icon = (ImageView)row.findViewById(R.id.image);
        icon.setImageResource(arr_images[position]);

        return row;
    }

    // Number of elements/rows in the spinner associated with the adapter
    @Override
    public int getCount() {
        return 6;
    }
}
