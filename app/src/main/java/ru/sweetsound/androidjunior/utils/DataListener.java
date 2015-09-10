package ru.sweetsound.androidjunior.utils;


public interface DataListener {
    void onChange(int position, String newData);

    void onAdd(String newData);
}
