package in.vilik.tamkapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.vilik.tamkapp.fragments.MenuFragment;
import in.vilik.tamkapp.fragments.TimetableFragment;
import in.vilik.tamkapp.utils.API;

/**
 * Implements pager adapter to enable swiping between views.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0425
 * @since 1.7
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    /**
     * Context of the adapter.
     */
    private Context context;

    /**
     * Constructs the object.
     *
     * @param fm        FragmentManager
     * @param context   Context
     */
    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    /**
     * Determines which view to display in which tab.
     *
     * @param position  Tab index
     * @return          Fragment to display in the specified tab
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimetableFragment.newInstance();
            case 1:
                return MenuFragment.newInstance(API.Type.CAMPUSRAVITA_MENU);
            case 2:
                return MenuFragment.newInstance(API.Type.PIRTERIA_MENU);
        }

        return null;
    }

    /**
     * Gets total number of tabs.
     *
     * @return  Total number of tabs
     */
    @Override
    public int getCount() {
        // Total amount of pages.
        return 3;
    }

    /**
     * Determines titles for each tab.
     *
     * @param position  Tab index
     * @return          Title for the specified tab
     */
    @Override
    public CharSequence getPageTitle(int position) {
        // Tab titles
        switch (position) {
            case 0:
                return context.getString(R.string.tab_header_timetable);
            case 1:
                return context.getString(R.string.tab_header_campusravita);
            case 2:
                return context.getString(R.string.tab_header_pirteria);
        }

        return null;
    }
}
