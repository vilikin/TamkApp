package in.vilik.tamkapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.vilik.tamkapp.fragments.MenuFragment;
import in.vilik.tamkapp.fragments.TimetableFragment;
import in.vilik.tamkapp.utils.API;

/**
 * Created by vili on 10/04/2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TimetableFragment.newInstance(position + 1);
            case 1:
                return MenuFragment.newInstance(API.Type.CAMPUSRAVITA_MENU);
            case 2:
                return MenuFragment.newInstance(API.Type.PIRTERIA_MENU);
        }

        return null;
    }

    @Override
    public int getCount() {
        // Total amount of pages.
        return 3;
    }

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
