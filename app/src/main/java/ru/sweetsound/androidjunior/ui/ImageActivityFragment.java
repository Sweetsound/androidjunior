package ru.sweetsound.androidjunior.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.sweetsound.androidjunior.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageActivityFragment extends Fragment {
    private ImageView mImage;

    public ImageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        mImage = (ImageView) v.findViewById(R.id.image);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        Uri imageUri = intent.getParcelableExtra(ImageActivity.EXTRA_IMAGE);
        mImage.setImageBitmap(BitmapFactory.decodeFile(getGalleryImage(imageUri)));
    }

    public String getGalleryImage(Uri imageUri){
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        String imageString = null;
        Cursor cursor = getActivity().getContentResolver().query(imageUri,
                filePathColumn, null, null, null);
        if (cursor!=null&&cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imageString = cursor.getString(columnIndex);
        }
        cursor.close();
        return imageString;
    }
}
