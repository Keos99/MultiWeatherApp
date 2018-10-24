package ru.geekbrains.multiweather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

public class DeviceLocation implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 10;
    private LocationManager locationManager;
    private String provider;
    private Context context;
    private Activity activity;
    private Geocoder geocoder;
    private List adress;
    private LocationListener locationListener;
    private String city;

    public DeviceLocation(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.geocoder = new Geocoder(context);
    }

    public String getCity() {
        return city;
    }

    @SuppressLint("MissingPermission")
    public void requestLocation() {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            locationManager.requestSingleUpdate(provider, locationListener, null);
        }
    }

    public void locationListener(){
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null){
                    getCity(location.getLatitude(),location.getLongitude());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    public boolean isHavePremision(){
        return ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(activity,
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
                requestLocation();
            }
        }
    }

    /*public void setCity (double lat, double longlat, TextView textView) {
           try {
               adress = geocoder.getFromLocation(lat,longlat,1);
               Address address = (Address) adress.get(0);
               String city = address.getAdminArea();
               textView.setText(city);
               textView.setTextSize(20);
           } catch (IOException e) {
               e.printStackTrace();
           }
    }*/

    public void getCity (double lat, double longlat){
        try {
            adress = geocoder.getFromLocation(lat,longlat,1);
            Address address = (Address) adress.get(0);
            city = address.getAdminArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

