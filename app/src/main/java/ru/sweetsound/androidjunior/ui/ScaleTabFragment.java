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
import android.os.Environment;
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

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.sweetsound.androidjunior.R;


public class ScaleTabFragment extends Fragment {

    private final static int GALLERY_IMAGE = 1;
    public final static int CAMERA_IMAGE = 2;
    private final static String TYPE_IMAGE = "image/*";
    private final static String FILE_IMAGE = "image";
    private File mFile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scale_tab, container, false);
        (view.findViewById(R.id.open_gallery))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    galleryIntent.setType(TYPE_IMAGE);
                } else {
                    galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(galleryIntent, GALLERY_IMAGE);
            }
                });
        (view.findViewById(R.id.take_photo))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    mFile = File.createTempFile(
                            FILE_IMAGE,  /* prefix */
                            ".jpg",         /* suffix */
                            f    /* directory */
                    );
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
                    startActivityForResult(takePictureIntent, CAMERA_IMAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            // When an Image is picked
            if (requestCode == GALLERY_IMAGE
                    && resultCode == Activity.RESULT_OK
                    && data != null) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                Intent intent = new Intent(getActivity(),ImageActivity.class);
                intent.putExtra(ImageActivity.EXTRA_IMAGE,selectedImage);
                startActivity(intent);

            }
            if (requestCode == CAMERA_IMAGE
                    && resultCode == Activity.RESULT_OK) {
                Uri selectedImage = Uri.fromFile(mFile);
                Intent intent = new Intent(getActivity(),ImageActivity.class);
                intent.putExtra(ImageActivity.EXTRA_IMAGE,selectedImage);
                startActivity(intent);

            }
    }



}
