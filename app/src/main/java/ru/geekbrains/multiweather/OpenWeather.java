package ru.geekbrains.multiweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.geekbrains.multiweather.model.WeatherRequest;

public interface OpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry,
                                     @Query("units") String unitsStandart,
                                     @Query("appid") String keyApi);
}

