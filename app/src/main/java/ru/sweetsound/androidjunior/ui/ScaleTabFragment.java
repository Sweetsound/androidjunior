package ru.sweetsound.androidjunior.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Target;

import ru.sweetsound.androidjunior.R;


public class ScaleTabFragment extends Fragment {

    private static int GALLERY_IMAGE = 1;
    private ImageView imgView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale_tab, container, false);
        imgView = (ImageView) view.findViewById(R.id.imgView);
        ((ImageButton) view.findViewById(R.id.open_gallery))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    galleryIntent.setType("image/*");
                } else {
                    galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(galleryIntent, GALLERY_IMAGE);
            }
                });
        ((ImageButton) view.findViewById(R.id.take_photo))
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
                Intent intent = new Intent(getActivity(),ImageActivity.class);
                intent.putExtra(ImageActivity.EXTRA_IMAGE,selectedImage);
                startActivity(intent);

            } else {
                Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }




}
