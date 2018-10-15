package ru.geekbrains.multiweather.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.geekbrains.multiweather.GreetingBuilder;
import ru.geekbrains.multiweather.GreetingStrings;
import ru.geekbrains.multiweather.OpenWeather;
import ru.geekbrains.multiweather.R;
import ru.geekbrains.multiweather.TemperatureColors;
import ru.geekbrains.multiweather.model.WeatherRequest;

public class WeatherFragment extends Fragment implements GreetingStrings, TemperatureColors {

    private TextView greetingTextView;
    private TextView cityTextView;
    private TextView temperatureTextView;
    private OpenWeather openWeather;
    private Retrofit retrofit;
    private final String KEY_API = "e7c8e06f97b8906936f24f58e77a8ced";

    //private Integer temperature = null;

    public static Fragment newInstance (String city){
        WeatherFragment fragment = new WeatherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("city", city);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, null);
        greetingTextView = view.findViewById(R.id.tv_greeting);
        cityTextView = view.findViewById(R.id.tv_city);
        temperatureTextView = view.findViewById(R.id.tv_temperature);
        greetingTextView.setText(new GreetingBuilder(WeatherFragment.this).get());
        cityTextView.setText(getArguments().getString("city"));
        //temperature = new Random().nextInt(40);
        //temperatureTextView.setText(temperature + "");
        requestData();
        //temperatureTextView.setTextColor(new TemperatureColorSelector(this).get());
        //getActivity().startService(new Intent(getActivity(),WeatherAppService.class));
        return view;
    }

    private void requestData(){
        initRetorfit();
        requestRetrofit(getArguments().getString("city"),getActivity());
    }

    public void initRetorfit(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeather = retrofit.create(OpenWeather.class);
    }

    public void requestRetrofit(String city, final Activity activity){
        openWeather.loadWeather(city,"metric", KEY_API).enqueue(new Callback<WeatherRequest>() {
            @Override
            public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                if (response.body() != null){
                    temperatureTextView.setText(Float.toString(response.body().getMain().getTemp()));
                    Toast.makeText(activity,"Ответ получен",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WeatherRequest> call, Throwable t) {
                Toast.makeText(activity,"Ошибка получения",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public String getGreeter()
    {
        return getArguments().getString("name");
    }

    @Override
    public String getMoring()
    {
        return getString(R.string.morning);
    }

    @Override
    public String getAfternoon()
    {
        return getString(R.string.afternoon);
    }

    @Override
    public String getEvening()
    {
        return getString(R.string.evening);
    }

    @Override
    public String getNight() {
        return getString(R.string.night);
    }

    @Override
    public int getColdColor()
    {
        return getResources().getColor(R.color.blue);
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
