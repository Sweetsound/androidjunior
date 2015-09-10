package ru.sweetsound.androidjunior.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import ru.sweetsound.androidjunior.R;

public class ImageActivity extends FragmentActivity {
    public final static String EXTRA_IMAGE = "extra_image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }

}
