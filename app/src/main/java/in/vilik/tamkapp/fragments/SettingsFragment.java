package in.vilik.tamkapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.Fragment;

import java.util.Set;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MealTypes;
import in.vilik.tamkapp.utils.AppPreferences;

/**
 * Implements a settings fragment.
 *
 * @author Vili Kinnunen vili.kinnunen@cs.tamk.fi
 * @version 2017.0504
 * @since 1.7
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Separator to use with included meals.
     */
    final String INCLUDED_MEALS_SEPARATOR = ", ";

    /**
     * Preferences of the app.
     */
    private AppPreferences preferences;

    /**
     * Constructs SettingsFragment.
     */
    public SettingsFragment() {
    }

    /**
     * Resumes fragment.
     */
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Pauses fragment.
     */
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Creates fragment.
     *
     * @param savedInstanceState    Saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new AppPreferences(getActivity());

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        updateSummaries();
    }

    /**
     * Triggers when preferences are changed. Updates summaries.
     *
     * @param sharedPreferences     Shared preferences
     * @param key                   Key that was changed
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummaries();
    }

    /**
     * Updates summaries based on preferences.
     */
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

    /**
     * Gets summary for selected timetable period.
     *
     * @param selectedTimetablePeriod   Selected timetable period
     * @return                          Summary for period
     */
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

    /**
     * Gets summary for selected meal types.
     *
     * @param selectedItems     Selected meal types
     * @param normalArrayId     String array resource for meal types
     * @param readableArrayId   String array resource for readable meal types
     * @return                  Summary for selected meal types
     */
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
