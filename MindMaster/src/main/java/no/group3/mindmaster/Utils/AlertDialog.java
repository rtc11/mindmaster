package no.group3.mindmaster.Utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.AlertDialog.Builder;

import no.group3.mindmaster.R;

/**
 * Created by JeppeE on 25/03/14.
 */
public class AlertDialog extends DialogFragment {

    private boolean isWinner = false;

    public AlertDialog(boolean isWinner) {
        this.isWinner = isWinner;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        android.app.AlertDialog.Builder builder = new Builder(getActivity());
        if (isWinner) {
            builder.setMessage(R.string.game_won)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }
        else {
            builder.setMessage(R.string.game_lost)
                    .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
        }
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
