package no.group3.mindmaster.Utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog.Builder;

import no.group3.mindmaster.MainActivity;
import no.group3.mindmaster.R;
import no.group3.mindmaster.Views.MainMenu;

/**
 * Created by JeppeE on 25/03/14.
 */
public class AlertDialog extends DialogFragment {

    private boolean isWinner = false;
    private Context context;

    public AlertDialog(boolean isWinner, Context context) {
        this.isWinner = isWinner;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        android.app.AlertDialog.Builder builder = new Builder(getActivity());
        if (isWinner) {
            builder.setMessage(R.string.game_won)
                    .setPositiveButton(R.string.rematch_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity ma = MainActivity.getInstance();
                            ma.startGameFragment();
                        }
                    })
                    .setNegativeButton(R.string.no_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {

                        }
                    });
        }
        else {
            builder.setMessage(R.string.game_lost)
                    .setPositiveButton(R.string.rematch_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
