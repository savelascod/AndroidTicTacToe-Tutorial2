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
public class QuitAlertDialog extends DialogFragment {

    AlertPositiveQuitListener alertPositiveQuitListener;
    OnClickListener quitListener = new OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            alertPositiveQuitListener.onPositiveQuitClick(true);
        }
    };

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            alertPositiveQuitListener = (AlertPositiveQuitListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement AlertPositiveQuitListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        /** Creating a Builder for the alert window **/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        builder.setTitle("Quit Game");

        /** Setting a label for the alert dialog**/
        builder.setMessage("Are you sure you want to quit the game?");

        /** Setting a positive button and its listener **/
        builder.setPositiveButton("Yes", quitListener);

        /** Creating the alert dialog window using the builder class */
        AlertDialog alertDialog = builder.create();

        /** Return the alert dialog window */
        return alertDialog;
    }

    interface AlertPositiveQuitListener {
        public void onPositiveQuitClick(boolean quit);
    }
}
