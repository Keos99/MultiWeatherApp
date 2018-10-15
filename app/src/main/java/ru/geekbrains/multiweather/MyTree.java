package ru.geekbrains.multiweather;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class MyTree extends Timber.DebugTree
{
    @Override
    protected void log(int priority, String tag, @NotNull String message, Throwable t)
    {
        super.log(priority, tag, message, t);
    }
}
