package edu.harding.tictactoe;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;

/**
 * Created by mordreth on 9/16/15.
 */
public class AboutAlertDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();

        /** Creating a Builder for the alert window **/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting a title for the window */
        builder.setTitle("About this Game");

        /** Setting items to the alert dialog */
        builder.setView(inflater.inflate(R.layout.about_dialog, null));

        /** Setting a positive button and its listener */
        builder.setPositiveButton("OK", null);

        /** Creating the alert dialog window using the builder class */
        AlertDialog alertDialog = builder.create();

        /** Return the alert dialog window */
        return alertDialog;
    }
}
