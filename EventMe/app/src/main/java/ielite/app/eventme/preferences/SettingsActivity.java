package ielite.app.eventme.preferences;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Suraj on 22-03-2016.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment())
                .commit();
    }
}
