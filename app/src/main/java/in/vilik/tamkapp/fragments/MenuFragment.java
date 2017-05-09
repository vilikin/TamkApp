package in.vilik.tamkapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;

import java.util.Locale;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.Campusravita;
import in.vilik.tamkapp.menus.Menu;
import in.vilik.tamkapp.menus.MenuList;
import in.vilik.tamkapp.menus.OnMenuUpdatedListener;
import in.vilik.tamkapp.menus.Pirteria;
import in.vilik.tamkapp.menus.recyclerview.MenuListAdapter;
import in.vilik.tamkapp.utils.API;
import in.vilik.tamkapp.utils.AppPreferences;
import in.vilik.tamkapp.utils.DataLoader;

/**
 * Implements a fragment containing a list of menus.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0504
 * @since 1.7
 */
public class MenuFragment extends Fragment {

    /**
     * Root view of the fragment.
     */
    View rootView;

    /**
     * MenuList displayed on this fragment.
     */
    MenuList menuList;

    /**
     * Flag: is the fragment and MenuList initialized.
     */
    boolean initialized;

    /**
     * RecyclerView containing the list of menus.
     */
    RecyclerView recyclerView;

    /**
     * Adapter for the RecyclerView containing the list of menus.
     */
    MenuListAdapter adapter;

    /**
     * Overlay for when there are no menu lists available.
     */
    LinearLayout noMenuListsOverlay;

    /**
     * Overlay for when there is no localization of the menu list available.
     */
    LinearLayout noLocalizationOverlay;
    AppPreferences preferences;
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Returns a new instance of this fragment for the given menu type.
     */
    public static Fragment newInstance(API.Type menuType) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt("type", menuType.ordinal());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates view for the fragment.
     *
     * @param inflater              Inflater
     * @param container             Container
     * @param savedInstanceState    Saved instance state
     * @return                      View for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initialized = false;

        preferences = new AppPreferences(getContext());

        rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMenu);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setRefreshing(true);

        noMenuListsOverlay = (LinearLayout) rootView.findViewById(R.id.empty_menulist_overlay);
        noLocalizationOverlay = (LinearLayout) rootView.findViewById(R.id.no_localization_menulist_overlay);

        Locale locale = Locale.getDefault();

        API.Type menuType = API.Type.values()[getArguments().getInt("type")];

        Debug.log("onCreateView()", "Creating view for menu type " + menuType.name());

        if (menuType == API.Type.CAMPUSRAVITA_MENU) {
            if (locale.getLanguage().equals("fi")) {
                menuList = new Campusravita(getActivity(), "fi");
            } else {
                menuList = new Campusravita(getActivity(), "en");
            }
        } else if (menuType == API.Type.PIRTERIA_MENU) {
            menuList = new Pirteria(getActivity());

            if (locale.getLanguage().equals("en")
                    && !preferences.isPirteriaMenuShownOnEnglishLocale()) {
                noLocalizationOverlay.setVisibility(View.VISIBLE);

                noLocalizationOverlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preferences.setPirteriaMenuShownOnEnglishLocale(true);
                        noLocalizationOverlay.setVisibility(View.GONE);
                    }
                });
            }
        }

        LinearLayoutManager manager = new LinearLayoutManager(getContext());

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

        menuList.setOnMenuUpdatedListener(new OnMenuUpdatedListener() {
            @Override
            public void onSuccess() {
                refreshMenuContent();
            }

            @Override
            public void onError() {
                refreshMenuContent();
            }
        });

        menuList.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                menuList.loadData(DataLoader.LoadingStrategy.SERVER_FIRST);
            }
        });

        return rootView;
    }

    /**
     * Resumes fragment.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (initialized) {
            Debug.log("onResume()", "Triggering loadData for " + menuList.getClass());
            menuList.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
        }
    }

    /**
     * Refreshes contents of the RecyclerView.
     */
    private void refreshMenuContent() {
        initialized = true;

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

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /**
     * Triggers when fragment is paused.
     */
    @Override
    public void onPause() {
        super.onPause();

        // Fixes a rare fragment overlapping bug
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }
    }
}
