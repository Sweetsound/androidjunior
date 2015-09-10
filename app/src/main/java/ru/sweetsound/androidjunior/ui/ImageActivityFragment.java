package ru.sweetsound.androidjunior.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.IOException;

import ru.sweetsound.androidjunior.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageActivityFragment extends Fragment {
    // we can be in one of these 3 states
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private final String TAG = "ImageActivityFragment";
    //constraint the minimal zoom we can have
    private final float MINZOOM = (float) 0.1;
    private ImageView mImage;
    private Button mZoomInBtn, mZoomOutBtn;
    private Matrix matrix = new Matrix();
    private Matrix savedMatrix = new Matrix();
    private int mode = NONE;
    // remember some things for zooming
    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;


    public ImageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);
        mImage = (ImageView) v.findViewById(R.id.image);
        mZoomInBtn = (Button) v.findViewById(R.id.button_zoom_in);
        mZoomOutBtn = (Button) v.findViewById(R.id.button_zoom_out);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        Uri imageUri = intent.getParcelableExtra(ImageActivity.EXTRA_IMAGE);
        mImage.setImageBitmap(getBitmapFromUri(imageUri));
        mImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(TAG, "touched event=" + event.toString());
                touchProcess(v, event);
                return true;
            }
        });
        mZoomInBtn.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = null;
            private float STANDARD_ZOOM = (float) 10 / 9;
            private long STANDARD_DELAY = 500;
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    matrix.postScale(STANDARD_ZOOM, STANDARD_ZOOM);
                    mImage.setImageMatrix(matrix);
                    mHandler.postDelayed(this, STANDARD_DELAY);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, STANDARD_DELAY);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return true;
            }
        });
        mZoomOutBtn.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = null;
            private float STANDARD_ZOOM = (float) 9 / 10;
            private long STANDARD_DELAY = 500;
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    matrix.postScale(STANDARD_ZOOM, STANDARD_ZOOM);
                    mImage.setImageMatrix(matrix);
                    mHandler.postDelayed(this, STANDARD_DELAY);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, STANDARD_DELAY);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return true;
            }
        });

    }


    private Bitmap getBitmapFromUri(Uri uri) {
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor =
                    getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
            return image;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void touchProcess(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                Log.i(TAG, "ACTION_DOWN");
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i(TAG, "ACTION_POINTER_DOWN");
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    float dx = event.getX() - start.x;
                    float dy = event.getY() - start.y;
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        matrix.set(savedMatrix);
                        float scale = (newDist / oldDist);
                        float[] values = new float[9];
                        matrix.getValues(values);
                        if (!(scale < 1) || !(scale * values[0] < MINZOOM)) {
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        float[] values = new float[9];
        matrix.getValues(values);
    }

    private void scaleMatrix(Matrix matrix, float scale) {
        matrix.postScale(scale, scale);
    }

    private void zoom(MotionEvent event) {

    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

}
