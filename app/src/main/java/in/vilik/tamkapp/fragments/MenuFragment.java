package in.vilik.tamkapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.menus.Menu;
import in.vilik.tamkapp.menus.MenuList;
import in.vilik.tamkapp.menus.OnMenuUpdatedListener;
import in.vilik.tamkapp.menus.Pirteria;
import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.DataLoader;
import in.vilik.tamkapp.utils.ErrorToaster;

/**
 * Created by vili on 10/04/2017.
 */

public class MenuFragment extends Fragment {
    View rootView;
    MenuList menuList;
    boolean initialized;
    RecyclerView recyclerView;
    MenuListAdapter adapter;
    LinearLayout noMenuListsOverlay;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(API.Type menuType) {
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

        API.Type menuType = API.Type.values()[getArguments().getInt("type")];

        Debug.log("onCreateView()", "Creating view for menu type " + menuType.name());

        if (menuType == API.Type.CAMPUSRAVITA_MENU) {
            menuList = new Campusravita(getActivity());
        } else if (menuType == API.Type.PIRTERIA_MENU) {
            menuList = new Pirteria(getActivity());
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMenu);
        noMenuListsOverlay = (LinearLayout) rootView.findViewById(R.id.empty_menulist_overlay);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        adapter = new MenuListAdapter(getContext(), menuList.getMenus());

        adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onParentExpanded(int parentPosition) {
                if (parentPosition == adapter.getParentList().size() - 1) {
                    int parentIndex = -1;

                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        if (adapter.isParentViewType(adapter.getItemViewType(i))) {
                            parentIndex++;

                            if (parentIndex == parentPosition) {
                                Menu parent = adapter.getParentList().get(parentPosition);
                                recyclerView.scrollToPosition(i + parent.getChildList().size());
                                return;
                            }
                        }
                    }
                }
            }

            @Override
            public void onParentCollapsed(int parentPosition) {

            }
        });

        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        menuList.setOnMenuUpdatedListener(new OnMenuUpdatedListener() {
            @Override
            public void onSuccess() {
                initialized = true;
                refreshMenuContent();
            }

            @Override
            public void onError() {
                initialized = true;

                ErrorToaster.show(getActivity(), ErrorToaster.ERROR_FAILED_TO_LOAD);
            }
        });

        menuList.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (initialized) {
            Debug.log("onResume()", "Triggering loadData for " + menuList.getClass());
            menuList.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
        }
    }

    private void refreshMenuContent() {
        Debug.log("refreshMenuContent()", "Refreshing menu content of " + menuList.getClass());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (menuList.getMenus().isEmpty()) {
                    noMenuListsOverlay.setVisibility(View.VISIBLE);
                } else {
                    noMenuListsOverlay.setVisibility(View.GONE);
                }

                adapter.notifyParentDataSetChanged(false);
            }
        });
    }
}
