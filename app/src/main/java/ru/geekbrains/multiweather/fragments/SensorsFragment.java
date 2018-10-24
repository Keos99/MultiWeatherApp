package ru.geekbrains.multiweather.fragments;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.geekbrains.multiweather.R;
import ru.geekbrains.multiweather.TemperatureColors;

public class SensorsFragment extends Fragment implements TemperatureColors {

    public static Fragment newInstance() {
        SensorsFragment sensorsFragment = new SensorsFragment();
        return sensorsFragment;
    }

    private TextView tvtemperaturesensornumber;
    private TextView tvhumiditynumber;
    private TextView tvtemperaturesensorstatus;
    private TextView tvhumiditysensorstatus;
    private SensorManager sensorManager;
    private Sensor temperaturesensor;
    private Sensor humiditysensor;
    private Activity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensors,null);
        tvtemperaturesensornumber = view.findViewById(R.id.tv_temperaturesensor_number);
        tvhumiditynumber = view.findViewById(R.id.tv_humidity_number);
        tvtemperaturesensorstatus = view.findViewById(R.id.tv_temperature_sensoe_status);
        tvhumiditysensorstatus = view.findViewById(R.id.tv_humidity_sensor_status);
        activity = getActivity();
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        temperaturesensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        humiditysensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorManager.registerListener(listenerTemperature,temperaturesensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(listenerHumidity,humiditysensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorStatus();
        return view;
    }

    private final SensorEventListener listenerTemperature = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (temperaturesensor != null)showTeperatureSensor(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private final SensorEventListener listenerHumidity = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (humiditysensor != null)showHumiditySensor(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private void showTeperatureSensor(SensorEvent sensorEvent){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sensorEvent.values[0]).append(" C");
        tvtemperaturesensornumber.setText(stringBuilder);
    }

    private void showHumiditySensor(SensorEvent sensorEvent){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sensorEvent.values[0]).append(" %");
        tvhumiditynumber.setText(stringBuilder);
    }

    private void sensorStatus (){
        StringBuilder temperatureSB = new StringBuilder();
        StringBuilder humiditySB = new StringBuilder();
        temperatureSB.append("Датчик температуры: ");
        if (temperaturesensor != null){
            temperatureSB.append("обнаружен!");
            tvtemperaturesensorstatus.setTextColor(getNormalColor());
        } else {
            temperatureSB.append("не обнаружен!");
            tvtemperaturesensorstatus.setTextColor(getHotColor());
        }
        tvtemperaturesensorstatus.setText(temperatureSB);

        humiditySB.append("Датчик влажности: ");
        if (humiditysensor != null){
            humiditySB.append("обнаружен!");
            tvhumiditysensorstatus.setTextColor(getNormalColor());
        } else {
            humiditySB.append("не обнаружен!");
            tvhumiditysensorstatus.setTextColor(getHotColor());
        }
        tvhumiditysensorstatus.setText(humiditySB);
    }

    @Override
    public int getColdColor() {
        return 0;
    }

    @Override
    public int getNormalColor()
    {
        return getResources().getColor(R.color.green);
    }

    @Override
    public int getHotColor()
    {
        return getResources().getColor(R.color.red);
    }
}
