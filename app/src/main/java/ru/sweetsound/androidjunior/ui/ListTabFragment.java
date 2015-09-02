package ru.sweetsound.androidjunior.ui;

import android.app.ListFragment;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import ru.sweetsound.androidjunior.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListTabFragment extends ListFragment {

    public ListTabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_tab, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        ListTabAdapter adapter = new ListTabAdapter(getActivity(),
                R.layout.list_item,
                R.id.item_text);
        setListAdapter(adapter);
    }

    public class ListTabAdapter extends ArrayAdapter<String> {

        LayoutInflater mInflater;
        private Context mContext;
        private int mResource;

        public ListTabAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                final View v = mInflater.inflate(mResource, parent, false);
                ((CheckBox) v.findViewById(R.id.list_checkbox))
                        .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                ImageView image = (ImageView) v.findViewById(R.id.list_yoba);
                                if (isChecked)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                        image.setImageDrawable(mContext.
                                                getDrawable(R.drawable.yoba_dissapointed));
                                    else image.setImageDrawable(mContext.getResources().
                                            getDrawable(R.drawable.yoba_dissapointed));
                                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                    image.setImageDrawable(mContext.
                                            getDrawable(R.drawable.yoba));
                                    else image.setImageDrawable(mContext.getResources().
                                                getDrawable(R.drawable.yoba));
                            }
                        });
                return v;
            }
            return convertView;
        }
    }

}
