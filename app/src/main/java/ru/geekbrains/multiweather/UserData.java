package ru.geekbrains.multiweather;

import java.io.Serializable;

public class UserData implements Serializable
{
    private String name;
    private String city;


    public UserData(String name, String city)
    {
        this.name = name;
        this.city = city;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
