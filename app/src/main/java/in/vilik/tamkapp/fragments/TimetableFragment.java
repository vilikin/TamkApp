package in.vilik.tamkapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.timetable.OnTimetableUpdatedListener;
import in.vilik.tamkapp.timetable.Timetable;
import in.vilik.tamkapp.utils.DataLoader;

/**
 * Created by vili on 10/04/2017.
 */

public class TimetableFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        final Timetable timetable = new Timetable(getContext());

        timetable.setOnTimetableUpdatedListener(new OnTimetableUpdatedListener() {
            @Override
            public void onSuccess() {
                System.out.println("SUCCESS!");
                System.out.println(timetable.toString());
            }

            @Override
            public void onError() {
                System.out.println("ERROR");
            }
        });

        timetable.loadData(DataLoader.LoadingStrategy.SERVER_FIRST);
        //TextView textView = (TextView) rootView.findViewById(R.id.section_label_timetable);
        //textView.setText();
        return rootView;
    }
}
