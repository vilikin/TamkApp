package in.vilik.tamkapp.fragments;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.menus.MenuList;
import in.vilik.tamkapp.menus.MenuType;
import in.vilik.tamkapp.menus.OnMenuLoadedListener;
import in.vilik.tamkapp.menus.Pirteria;

/**
 * Created by vili on 10/04/2017.
 */

public class MenuFragment extends Fragment {
    View rootView;
    MenuList menuList;
    boolean initialized;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(MenuType menuType) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt("type", menuType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initialized = false;

        rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        MenuType menuType = MenuType.values()[getArguments().getInt("type")];

        Debug.log("onCreateView()", "Creating view for menu type " + menuType.toString());

        if (menuType == MenuType.CAMPUSRAVITA) {
            menuList = new Campusravita(getActivity());
        } else if (menuType == MenuType.PIRTERIA) {
            menuList = new Pirteria(getActivity());
        }

        menuList.setOnMenuLoadedListener(new OnMenuLoadedListener() {
            @Override
            public void loaded() {
                initialized = true;
                refreshMenuContent();
            }
        }, true);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        Debug.log("onResume()", "Resumed menu fragment for " + menuList.getClass());

        if (initialized) {
            Debug.log("onResume()", "Triggering refresh for " + menuList.getClass());
            menuList.refresh();
        }
    }

    private void refreshMenuContent() {
        Debug.log("refreshMenuContent()", "Refreshing menu content of " + menuList.getClass());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                textView.setText(menuList.toString());
            }
        });
    }
}
