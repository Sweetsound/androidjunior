package ru.sweetsound.androidjunior.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.widget.TabHost;
import android.widget.Toast;

import ru.sweetsound.androidjunior.R;

public class TabHostActivity extends AppCompatActivity {

    private FragmentTabHost mTabHost = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_host);
        initTabHost();
    }

    private void initTabHost() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_list_tag))
                        .setIndicator(getResources().getString(R.string.tab_list), null),
                ListTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_scaling_tag))
                        .setIndicator(getResources().getString(R.string.tab_scaling), null),
                ScaleTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_service_tag))
                        .setIndicator(getResources().getString(R.string.tab_service), null),
                ServiceTabFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(getResources().getString(R.string.tab_map_tag))
                        .setIndicator(getResources().getString(R.string.tab_map), null),
                MapTabFragment.class, null);


        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getBaseContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
