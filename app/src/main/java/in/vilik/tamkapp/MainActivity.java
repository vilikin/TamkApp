package in.vilik.tamkapp;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;

import java.util.Calendar;

import in.vilik.tamkapp.utils.AppPreferences;

public class MainActivity extends AppCompatActivity {
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

        AppPreferences preferences = new AppPreferences(this);

        // If application hasn't been started before, open settings activity.
        if (preferences.isFirstLaunch()) {
            preferences.setFirstLaunch(false);
            //startActivity(toSettingsIntent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

                if (position != 0) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addDeadline(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("fullDay", true);
        startActivity(intent);
    }

    private void addDeadline(Calendar calendar) {
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("date", calendar);
        intent.putExtra("fullDay", true);
        startActivity(intent);
    }
}
