package ru.sweetsound.androidjunior.ui;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import ru.sweetsound.androidjunior.R;
import ru.sweetsound.androidjunior.utils.DataListener;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListTabFragment extends ListFragment implements DataListener{

    public final static int LIST_FRAGMENT = 1;
    private final static String TAG = "ListTabFragment.java";
    private ArrayList<String> array;
    private ListTabAdapter mAdapter;
    public ListTabFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tab_host, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_add) {
            addItem();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_tab, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<String> array = new ArrayList<>();
        //T0D0 fill from file
         mAdapter = new ListTabAdapter(getActivity(),
                R.layout.list_item,
                R.id.item_text,
                 array);
        setListAdapter(mAdapter);

    }

    public void addItem(){
        ElementDialogFragment dialog = new ElementDialogFragment();
        dialog.setTargetFragment(this,LIST_FRAGMENT);
        dialog.show(getFragmentManager(), null);

    }

    public void changeItem(int position, String data) {
        ElementDialogFragment dialog = new ElementDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ElementDialogFragment.DATA_KEY, data);
        bundle.putInt(ElementDialogFragment.POSITION_KEY,
                position);
        dialog.setArguments(bundle);
        dialog.setTargetFragment(this, LIST_FRAGMENT);
        dialog.show(getFragmentManager(), null);
    }

    @Override
    public void onChange(int position, String newData) {
        mAdapter.insertIntoArray(newData, position);
    }

    @Override
    public void onAdd(String newData) {
        mAdapter.addToArray(newData);
    }


    public class ListTabAdapter extends ArrayAdapter<String>{

        LayoutInflater mInflater;
        private Context mContext;
        private ArrayList<String> mArray;

        public ListTabAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> array) {
            super(context, resource, textViewResourceId, array);
            mInflater = LayoutInflater.from(context);
            mContext = context;
            mArray = array;
        }

        public void insertIntoArray(String newData, int position){
           // Log.i(TAG,"insert into array position" + position);
         //   insert(newData, position);
            mArray.set(position, newData);
            notifyDataSetChanged();
        }

        public void addToArray(String newData){
         //   add(newData);
         //   Log.i(TAG,"add to array");
            mArray.add(newData);
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
                final View v = super.getView(position,convertView,parent);
                final TextView textView = (TextView) v.findViewById(R.id.item_text);
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

                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG,"on click" + position);
                        changeItem(position,textView.getText().toString());
                    }
                });
                return v;
        }



    }

}
