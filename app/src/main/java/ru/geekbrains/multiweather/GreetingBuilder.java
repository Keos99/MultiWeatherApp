package ru.geekbrains.multiweather;

import java.util.Calendar;

public class GreetingBuilder {
    private int currentHour;
    private GreetingStrings greetingStrings;

    public GreetingBuilder(GreetingStrings greetingStrings) {
        this.greetingStrings = greetingStrings;
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    public GreetingBuilder(GreetingStrings greetingStrings, int currentHour) {
        this.greetingStrings = greetingStrings;
        this.currentHour = currentHour;
    }

    public String get() {
        if (5 <= currentHour && currentHour < 12) {
            return String.format("%s!", greetingStrings.getMoring());
        } else if (12 <= currentHour && currentHour < 18) {
            return String.format("%s!", greetingStrings.getAfternoon());
        } else if (18 <= currentHour && currentHour < 21) {
            return String.format("%s!", greetingStrings.getEvening());
        } else {
            return String.format("%s!", greetingStrings.getNight());
        }
    }
}
