package in.vilik.tamkapp;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import in.vilik.tamkapp.fragments.MenuFragment;
import in.vilik.tamkapp.fragments.TimetableFragment;
import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.menus.MenuType;
import in.vilik.tamkapp.menus.Pirteria;

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
                return MenuFragment.newInstance(MenuType.CAMPUSRAVITA);
            case 2:
                return MenuFragment.newInstance(MenuType.PIRTERIA);
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
                return "LUKKARI";
            case 1:
                return "CAMPUSRAVITA";
            case 2:
                return "PIRTERIA";
        }
        return null;
    }
}
