package ru.sweetsound.androidjunior.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import ru.sweetsound.androidjunior.R;


public class ScaleTabFragment extends Fragment {

    private static int GALLERY_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale_tab, container, false);
        ((ImageView) view.findViewById(R.id.open_gallery))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, GALLERY_IMAGE);
            }
                });
        ((ImageView) view.findViewById(R.id.take_photo))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //T0D0 camera shot
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == GALLERY_IMAGE
                    && resultCode == Activity.RESULT_OK
                    && data != null) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

    }




}
