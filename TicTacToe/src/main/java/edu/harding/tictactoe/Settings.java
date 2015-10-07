package edu.harding.tictactoe;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by mordreth on 10/7/15.
 */
public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.preferences);
    }
}
