package ru.geekbrains.multiweather;

public class TemperatureColorSelector
{
    private TemperatureColors temperatureColors;

    public TemperatureColorSelector(TemperatureColors temperatureColors)
    {
        this.temperatureColors = temperatureColors;
    }

    public int get(int temperature)
    {
        if (temperature < 15)
        {
            return temperatureColors.getColdColor();
        }
        else if (temperature < 25)
        {
            return temperatureColors.getNormalColor();
        }
        else
        {
            return temperatureColors.getHotColor();
        }
    }
}
