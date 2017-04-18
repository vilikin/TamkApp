package in.vilik.tamkapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.vilik.tamkapp.Debug;
import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MenuList;
import in.vilik.tamkapp.menus.recyclerview.MenuListAdapter;
import in.vilik.tamkapp.timetable.OnTimetableUpdatedListener;
import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.timetable.recyclerview.TimetableAdapter;
import in.vilik.tamkapp.utils.DataLoader;

/**
 * Created by vili on 10/04/2017.
 */

public class TimetableFragment extends Fragment {
    View rootView;
    Timetable timetable;
    boolean initialized;
    RecyclerView recyclerView;
    TimetableAdapter adapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance() {
        return new TimetableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initialized = false;

        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerViewTimetable);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());

        timetable = new Timetable(getContext());

        adapter = new TimetableAdapter(timetable);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        timetable.setOnTimetableUpdatedListener(new OnTimetableUpdatedListener() {
            @Override
            public void onSuccess() {
                System.out.println("TIMETABLE LOADED");
                refresh();
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });

        timetable.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (initialized) {
            Debug.log("onResume()", "Triggering loadData for Timetable");
            timetable.loadData(DataLoader.LoadingStrategy.CACHE_FIRST);
        }
    }

    private void refresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                initialized = true;
            }
        });
    }
}
