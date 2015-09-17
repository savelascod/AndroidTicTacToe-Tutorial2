package edu.harding.tictactoe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

/**
 * Created by mordreth on 9/16/15.
 */
public class DifficultyAlertDialog extends DialogFragment {

    AlertPositiveListener alertPositiveListener;
    OnClickListener submitListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            AlertDialog alert = (AlertDialog) dialog;
            int position = alert.getListView().getCheckedItemPosition();
            alertPositiveListener.onPositiveClick(position);
        }
    };

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            alertPositiveListener = (AlertPositiveListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement DialogPositiveListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int position = bundle.getInt("position");

        /** Creating a Builder for the alert window **/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        builder.setTitle("Select Difficulty");

        /** Setting items to the alert dialog */
        builder.setSingleChoiceItems(TicTacToeGame.difficultyLevels, position, null);

        /** Setting a positive button and its listener */
        builder.setPositiveButton("OK", submitListener);

        /** Setting a positive button and its listener */
        builder.setNegativeButton("Cancel", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog alertDialog = builder.create();

        /** Return the alert dialog window */
        return alertDialog;
    }

    interface AlertPositiveListener {
        public void onPositiveClick(int position);
    }
}
