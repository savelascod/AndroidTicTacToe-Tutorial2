package edu.harding.tictactoe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.*;

/**
 * Created by mordreth on 10/7/15.
 */
public class Settings extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        addPreferencesFromResource(R.xml.preferences);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        final ListPreference difficultyLevelPref = (ListPreference) findPreference("difficulty_level");
        final String difficulty =
                preferences.getString("difficulty_level", getResources().getString(R.string.difficulty_expert));
        difficultyLevelPref.setSummary(difficulty);
        difficultyLevelPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                difficultyLevelPref.setSummary((CharSequence) newValue);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("difficulty_level", newValue.toString());
                editor.commit();
                return true;
            }

        });

        final EditTextPreference victoryMessagePref = (EditTextPreference) findPreference("victory_message");
        String victoryMessage = preferences.getString("victory_message", getResources().getString(R.string.result_human_win));
        victoryMessagePref.setSummary(victoryMessage);
        victoryMessagePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                victoryMessagePref.setSummary((CharSequence) newValue);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("victory_message", newValue.toString());
                editor.commit();
                return true;
            }
        });

    }
}
