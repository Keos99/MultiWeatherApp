package ru.geekbrains.multiweather.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.multiweather.CitiesRVAdapter;
import ru.geekbrains.multiweather.DeviceLocation;
import ru.geekbrains.multiweather.ListItem;
import ru.geekbrains.multiweather.R;

public class CitiesFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {

    private RecyclerView recyclerView;
    private CitiesRVAdapter citiesRVAdapter;
    private SharedPreferences sharedPreferencesSettings;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private DeviceLocation deviceLocation;
    private static final int PERMISSION_REQUEST_CODE = 10;

    public static Fragment newInstance(){
        CitiesFragment fragment = new CitiesFragment();
        return fragment;
    }

    FragmentManager.OnBackStackChangedListener backStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
        }
    };

    boolean isMasterDetail = false;
    int currentIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        deviceLocation = new DeviceLocation(getContext(),getActivity());
        deviceLocation.locationListener();
        sharedPreferencesSettings = getActivity().getSharedPreferences("Settings",Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferencesSettings.edit();
        defineCity();
        View view = inflater.inflate(R.layout.fragment_cities, null);
        recyclerView = view.findViewById(R.id.rv_cities);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<ListItem> items =createList();
        citiesRVAdapter = new CitiesRVAdapter(items, R.layout.item_cities_rv_adapter, new CitiesRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ListItem item, int pos) {
                onItemClickClick(item,pos);
            }
        });
        recyclerView.setAdapter(citiesRVAdapter);
        isMasterDetail = getActivity().findViewById(R.id.fl_master) != null;
        return view;
    }

    public void defineCity(){
        if (haveSavedSettings()){
            settingsLoad();
        } else {
            if (deviceLocation.isHavePremision()){
                deviceLocation.requestLocation();
                Toast.makeText(getContext(),deviceLocation.getCity(),Toast.LENGTH_LONG);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_master,
                        WeatherFragment.newInstance(deviceLocation.getCity())).commit();
            } else {
                requestLocationPermissions();
            }
        }
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length == 2 &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED ||
                            grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                deviceLocation.requestLocation();
            }
        }
    }

    private List<ListItem> createList(){
        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("Moscow"));

        return items;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getActivity().getSupportFragmentManager().removeOnBackStackChangedListener(backStackChangedListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("index", 0);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("index", currentIndex);
    }

    public void onItemClickClick(ListItem item, int pos){
        String city = item.getCity();
        settingsSave(city);
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fl_master);
        if (isMasterDetail) {
            if (fragment instanceof WeatherFragment) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_master, WeatherFragment.newInstance(city))
                    .commit();
        } else {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_master, WeatherFragment.newInstance(city))
                    .addToBackStack(city).commit();
        }
    }

    private boolean haveSavedSettings(){
        String settingsCity = sharedPreferencesSettings.getString("city","-1");
        if (sharedPreferencesSettings != null && !settingsCity.contains("-1")){
            return true;
        }
        return false;
    }

    private void settingsLoad(){
        if (haveSavedSettings()){
            String settingsCity = sharedPreferencesSettings.getString("city","-1");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_master,WeatherFragment.newInstance(settingsCity))
                    .commit();
        }
    }

    private void settingsSave(String city){
        sharedPreferencesEditor.putString("city",city).commit();
    }
}
