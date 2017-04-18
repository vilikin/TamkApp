package in.vilik.tamkapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Set;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MealTypes;
import in.vilik.tamkapp.utils.AppPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    final String INCLUDED_MEALS_SEPARATOR = ", ";
    private AppPreferences preferences;

    public SettingsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new AppPreferences(getActivity());

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        updateSummaries();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummaries();
    }

    private void updateSummaries() {

        findPreference(preferences.getKeyCampusravitaMeals())
                .setSummary(getSummaryForSelectedListItems(preferences.getCampusravitaMeals(),
                        R.array.campusravita_meals, R.array.campusravita_meals_readable));

        findPreference(preferences.getKeyPirteriaMeals())
                .setSummary(getSummaryForSelectedListItems(preferences.getPirteriaMeals(),
                        R.array.pirteria_meals, R.array.pirteria_meals_readable));

        String selectedStudentGroup = preferences.getTimetableStudentGroup();
        String studentGroupSummary;

        if (selectedStudentGroup.trim().isEmpty()) {
            studentGroupSummary = getResources().getString(R.string.preference_summary_default_timetable_student_group);
        } else {
            studentGroupSummary = getResources()
                    .getString(R.string.preference_summary_timetable_student_group, selectedStudentGroup);
        }

        findPreference(preferences.getKeyTimetableStudentGroup())
                .setSummary(studentGroupSummary);

        String selectedTimetablePeriod = preferences.getTimetablePeriod();
        String timetablePeriodSummary = getSummaryForSelectedPeriod(selectedTimetablePeriod);

        findPreference(preferences.getKeyTimetablePeriod())
                .setSummary(timetablePeriodSummary);
    }

    private String getSummaryForSelectedPeriod(String selectedTimetablePeriod) {
        String[] periods = getResources().getStringArray(R.array.timetable_periods);
        String[] summaries = getResources().getStringArray(R.array.timetable_periods_summary);

        for (int i = 0; i < periods.length; i++) {
            if (periods[i].equals(selectedTimetablePeriod)) {
                return summaries[i];
            }
        }

        return null;
    }

    private String getSummaryForSelectedListItems(Set<String> selectedItems,
                                                  int normalArrayId, int readableArrayId) {
        if (selectedItems.size() > 0) {
            StringBuilder summary = new StringBuilder();

            for (String selectedItem : selectedItems) {
                String readableType = MealTypes
                        .getReadableMealType(getActivity(), normalArrayId,
                                readableArrayId, selectedItem);

                summary.append(INCLUDED_MEALS_SEPARATOR).append(readableType);
            }

            return getString(R.string.preference_summary_selected_meals, summary.substring(INCLUDED_MEALS_SEPARATOR.length()));
        } else {
            return getString(R.string.preference_summary_empty_meals);
        }
    }
}
