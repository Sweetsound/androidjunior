package ru.sweetsound.androidjunior.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.sweetsound.androidjunior.R;
import ru.sweetsound.androidjunior.utils.Quote;
import ru.sweetsound.androidjunior.utils.ServiceXMLParser;

public class ServiceTabFragment extends ListFragment {

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
        new UriAsyncTask().execute(path,null,null);
    }

    private void addDataToList(String xml) {
        ArrayList<Quote> list = null;
        try {
             list = new ServiceXMLParser().parseResult(xml);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        if (list != null) {
            ServiceListAdapter adapter = new ServiceListAdapter(getActivity(),
                    R.layout.service_list_item,
                    list);
            setListAdapter(adapter);
        }
    }


    private class ServiceListAdapter extends ArrayAdapter<Quote> {


        private LayoutInflater mInflater;
        private int mResource;
        public ServiceListAdapter(Context context, int resource, List<Quote> objects) {
            super(context, resource, objects);
            mResource = resource;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
        if (convertView == null) {
            View v = mInflater.inflate(mResource,parent,false);
            TextView text = (TextView) v.findViewById(R.id.item_text);
            text.setText(getItem(position).getText() != null? getItem(position).getText() :
                    getResources().getString(R.string.no_info));
            TextView date = (TextView) v.findViewById(R.id.item_date);
            date.setText(getItem(position).getDate() != null? getItem(position).getDate() :
                    getResources().getString(R.string.no_info));
            TextView id = (TextView) v.findViewById(R.id.item_id);
            id.setText(getItem(position).getId() != null? getItem(position).getId() :
                getResources().getString(R.string.no_info));
            return v;
            }
        return convertView;
        }
    }

    private class UriAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("RESPONSE","START");
            StringBuilder builder = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            if (url!=null) {
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
                    Log.i("RESPONSE", "END");
                }
                return builder.toString();
            }
            return null;
    }
        public void onPostExecute(String result){
            isRequesting = false;
            mProgressDialog.hide();
            addDataToList(result);
        }

}
}
