package ru.geekbrains.multiweather.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ru.geekbrains.multiweather.R;
import ru.geekbrains.multiweather.bd.CitiesDataReader;
import ru.geekbrains.multiweather.bd.CitiesDataSource;

public class SettingsFragment extends Fragment {

    private EditText etSettingsCity;
    private Button button;
    private SharedPreferences settings;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private String city;
    private CitiesDataSource citiesDataSource;

    public static Fragment newInstance (){
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,null);
        etSettingsCity = view.findViewById(R.id.et_settings_city);
        button = view.findViewById(R.id.b_settings);
        settings = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        sharedPreferencesEditor = settings.edit();
        citiesDataSource =  new CitiesDataSource(getActivity().getApplicationContext());
        buttonListener();
        return view;
    }

    private void buttonListener (){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_master, WeatherFragment.newInstance(city))
                        .commit();
            }
        });
    }

    private void saveSettings(){
        city = etSettingsCity.getText().toString();
        citiesDataSource.open();
        citiesDataSource.addCity(city);
        try {
            citiesDataSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sharedPreferencesEditor.putString("city",city).commit();
        Toast.makeText(getActivity(), getString(R.string.Saved), Toast.LENGTH_SHORT).show();
    }
}
