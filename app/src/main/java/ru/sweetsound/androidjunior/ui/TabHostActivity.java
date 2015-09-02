package ru.sweetsound.androidjunior.ui;

import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

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
        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();
        TabHost.TabSpec listTabSpec = mTabHost.newTabSpec(getResources().
                getString(R.string.tab_list_tag));
        listTabSpec.setIndicator(getResources().getString(R.string.tab_list));

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
