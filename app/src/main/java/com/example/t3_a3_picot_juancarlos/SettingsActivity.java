package com.example.t3_a3_picot_juancarlos;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.pojo.Cliente;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ListPreference datos = (ListPreference)findPreference("origen_datos");
            ListPreference lenguage = (ListPreference)findPreference("lenguage");
            Cliente conectado = (Cliente) getActivity().getIntent().getSerializableExtra("Cliente");
            CharSequence currText = datos.getEntry();
            String currValue = datos.getValue();

            Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    System.out.println(newValue.toString());
                    if(newValue.toString().equals("es") || newValue.toString().equals("en")) {
                        Resources resources = getResources();
                        DisplayMetrics metrics = resources.getDisplayMetrics();
                        Configuration configuration = resources.getConfiguration();
                        configuration.locale=new Locale(newValue.toString());
                        resources.updateConfiguration(configuration,metrics);
                        onConfigurationChanged(configuration);
                        Intent intent = new Intent(getActivity(), principal.class);
                        intent.putExtra("Cliente", conectado);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    return true;
                }
            };

            datos.setOnPreferenceChangeListener(listener);
            lenguage.setOnPreferenceChangeListener(listener);

        }
    }
}