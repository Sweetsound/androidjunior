package ru.sweetsound.androidjunior.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ru.sweetsound.androidjunior.R;

public class ServiceTabFragment extends Fragment {

    private final static String URI_PATH = "http://storage.space-o.ru/testXmlFeed.xml";
    private ProgressDialog mProgressDialog;
    private boolean isRequesting = false;
    private Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_tab, container, false);
        mButton = (Button) v.findViewById(R.id.getdata);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getXMLData(URI_PATH);
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        if (isRequesting) mProgressDialog.show();
    }

    private void getXMLData(final String path){
        isRequesting = true;
        mProgressDialog.show();
        String result = null;
        new UriAsyncTask().execute(path,null,result);
    }




    private class UriAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("RESPONSE","START");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                byte[] buffer = new byte[1024];
                while (in.read(buffer, 0, 1024) != -1) {
                    builder.append(new String(buffer));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
                Log.i("RESPONSE","END");
        }
            return builder.toString();
    }
        public void onPostExecute(String result){
            isRequesting = false;
            mProgressDialog.hide();
        }

}
}
