package in.vilik.tamkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.menus.MenuList;
import in.vilik.tamkapp.menus.Pirteria;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;

    ArrayList<String> placeholderGroups = new ArrayList<String>(){{
        add("15TIKOOT");
        add("16TIKO2");
    }};

    private Intent toSettingsIntent;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections.
     */
    private MainPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toSettingsIntent = new Intent(this, SettingsActivity.class);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.getBoolean("v1_first_launch", true)) {
            prefs.edit().putBoolean("v1_first_launch", false).apply();

            startActivity(toSettingsIntent);
        }

        //PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Check if group selector should be visible on the selected tab
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        toggleSpinner(true);
                        break;
                    default:
                        toggleSpinner(false);
                        break;
                }
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, placeholderGroups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(toSettingsIntent);
                break;
        }

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    public void toggleSpinner(boolean visible) {
        if (visible) {
            spinner.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.INVISIBLE);
        }
    }
}
