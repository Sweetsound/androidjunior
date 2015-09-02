package ru.sweetsound.androidjunior.ui;

import android.app.AlertDialog;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

import ru.sweetsound.androidjunior.R;

public class TabHostActivity extends AppCompatActivity {

    private TabHost mTabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        if (mTabHost == null) initTabHost();
    }

    private void initTabHost(){
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabHost.TabSpec listTabSpec = mTabHost.newTabSpec(getResources().
                getString(R.string.tab_list_tag))
                .setIndicator(getResources().getString(R.string.tab_list))
                .setContent(R.id.list_tab_fragment);

        TabHost.TabSpec scaleTabSpec = mTabHost.newTabSpec(getResources().
                getString(R.string.tab_scaling_tag))
                .setIndicator(getResources().getString(R.string.tab_scaling))
                .setContent(R.id.scaling_tab_fragment);

        TabHost.TabSpec serviceTabSpec = mTabHost.newTabSpec(getResources().
                getString(R.string.tab_service_tag))
                .setIndicator(getResources().getString(R.string.tab_service))
                .setContent(R.id.service_tab_fragment);

        TabHost.TabSpec mapTabSpec = mTabHost.newTabSpec(getResources().
                getString(R.string.tab_map_tag))
                .setIndicator(getResources().getString(R.string.tab_map))
                .setContent(R.id.map_tab_fragment);

        mTabHost.addTab(listTabSpec);
        mTabHost.addTab(scaleTabSpec);
        mTabHost.addTab(serviceTabSpec);
        mTabHost.addTab(mapTabSpec);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_host, menu);
        return true;
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

    public void addItem(){
        ElementDialogFragment dialog = new ElementDialogFragment();
      //  Bundle bundle = new Bundle();
      //  bundle.putString(ElementDialogFragment.DATA_KEY,s);
       // dialog.setArguments(bundle);
        dialog.show(getFragmentManager(),null);

    }
}
