package ru.geekbrains.multiweather;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;


public class WeatherAppService extends IntentService {

    public WeatherAppService() {
        super("WeatherAppService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }
}
