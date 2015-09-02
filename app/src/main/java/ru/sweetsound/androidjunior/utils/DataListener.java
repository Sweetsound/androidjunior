package ru.sweetsound.androidjunior.utils;

/**
 * Created by pasha on 02.09.2015.
 */
public interface DataListener {
    public void onChange(int position, String newData);
    public void onAdd(String newData);
}
