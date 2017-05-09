package in.vilik.tamkapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.OnTimetableUpdatedListener;
import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.recyclerview.TimetableAdapter;
import in.vilik.tamkapp.utils.DataLoader;

/**
 * Implements a fragment containing a timetable.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0504
 * @since 1.7
 */
public class TimetableFragment extends Fragment {

    /**
     * Timetable instance to display in the fragment.
     */
    Timetable timetable;

    /**
     * Flag: is fragment initialized.
     */
    boolean initialized;

    /**
     * RecyclerView that holds the timetable.
     */
    RecyclerView recyclerView;

    /**
     * Adapter for the RecyclerView.
     */
    TimetableAdapter adapter;

    /**
     * Swipe refresh layout.
     */
    SwipeRefreshLayout swipeRefreshLayout;

    /**
     * Returns a new instance of TimetableFragment.
     */
    public static Fragment newInstance() {
        return new TimetableFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView;
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setRefreshing(true);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerViewTimetable);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        Locale locale = Locale.getDefault();

        timetable = new Timetable(getContext(), locale.getLanguage());

        adapter = new TimetableAdapter(timetable);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        timetable.setOnTimetableUpdatedListener(new OnTimetableUpdatedListener() {
            @Override
            public void onSuccess() {
                Debug.log("onSuccess()", "Timetable ready, refreshing UI");
                refresh();
            }

            @Override
            public void onError() {
                refresh();
            }
        });

        timetable.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                timetable.loadData(DataLoader.LoadingStrategy.SERVER_FIRST);
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
            Debug.log("onResume()", "Triggering loadData for Timetable");
            timetable.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
        }
    }

    /**
     * Refreshes contents of the RecyclerView.
     */
    private void refresh() {
        initialized = true;

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();

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
