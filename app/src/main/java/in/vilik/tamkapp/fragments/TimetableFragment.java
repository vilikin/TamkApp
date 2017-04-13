package in.vilik.tamkapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.vilik.tamkapp.R;

/**
 * Created by vili on 10/04/2017.
 */

public class TimetableFragment extends Fragment {
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static Fragment newInstance(int sectionNumber) {
        TimetableFragment fragment = new TimetableFragment();
        Bundle args = new Bundle();
        args.putInt("section", sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_timetable, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label_timetable);
        textView.setText("THIS IS TIMETABLE FOR " + getArguments().getInt("section"));
        return rootView;
    }
}
