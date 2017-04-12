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
    /**
     * Group id selector located in the action bar.
     */
    Spinner spinner;

    ArrayList<String> placeholderGroups = new ArrayList<String>(){{
        add("15TIKOOT");
        add("16TIKO2");
    }};

    /**
     * An intent that leads to the settings activity.
     */
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

    /**
     * Bootstraps the application.
     * Setups tab manager, action bar and opens settings on first launch.
     *
     * @param savedInstanceState Saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For clearing preferences.
        // PreferenceManager.getDefaultSharedPreferences(this).edit().clear().apply();

        toSettingsIntent = new Intent(this, SettingsActivity.class);

        // Set default preferences on first launch.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // If application hasn't been started before, open settings activity.
        if (prefs.getBoolean("v1_first_launch", true)) {
            prefs.edit().putBoolean("v1_first_launch", false).apply();

            startActivity(toSettingsIntent);
        }

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

    /**
     * Populates action bar with appropriate menu items.
     *
     * @param menu  Menu
     * @return      Boolean
     */
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

    /**
     * Opens settings activity when corresponding menu option is selected.
     * @param item      Item that was selected
     * @return          Boolean
     */
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

    /**
     * Toggles group id selector visibility.
     *
     * @param visible   If the selector should be visible on the action bar
     */
    public void toggleSpinner(boolean visible) {
        if (visible) {
            spinner.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.INVISIBLE);
        }
    }
}
