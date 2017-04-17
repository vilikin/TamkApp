package in.vilik.tamkapp.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import java.util.HashSet;
import java.util.Set;

import in.vilik.tamkapp.R;
import in.vilik.tamkapp.menus.MealTypes;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private String key_campusravita_meals;
    private String key_pirteria_meals;
    private String key_student_group;
    private String key_timetable_period;
    private String key_timetable_show_weekends;

    final String INCLUDED_MEALS_SEPARATOR = ", ";

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

        key_campusravita_meals = getResources()
                .getString(R.string.preference_key_campusravita_meals);
        key_pirteria_meals = getResources()
                .getString(R.string.preference_key_pirteria_meals);
        key_student_group = getResources()
                .getString(R.string.preference_key_student_group);
        key_timetable_period = getResources()
                .getString(R.string.preference_key_timetable_period);
        key_timetable_show_weekends = getResources()
                .getString(R.string.preference_key_timetable_show_weekends);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        updateSummaries(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummaries(sharedPreferences);
    }

    private void updateSummaries(SharedPreferences sharedPreferences) {
        Set<String> campusravitaIncludedMeals = sharedPreferences.getStringSet(key_campusravita_meals,
                new HashSet<String>());

        findPreference(key_campusravita_meals)
                .setSummary(getSummaryForSelectedListItems(campusravitaIncludedMeals,
                        R.array.campusravita_meals, R.array.campusravita_meals_readable));

        Set<String> pirteriaIncludedMeals = sharedPreferences.getStringSet(key_pirteria_meals,
                new HashSet<String>());

        findPreference(key_pirteria_meals)
                .setSummary(getSummaryForSelectedListItems(pirteriaIncludedMeals,
                        R.array.pirteria_meals, R.array.pirteria_meals_readable));

        String selectedStudentGroup = sharedPreferences.getString(key_student_group, "");
        String studentGroupSummary;

        if (selectedStudentGroup.trim().isEmpty()) {
            studentGroupSummary = getResources().getString(R.string.group_summary_default);
        } else {
            studentGroupSummary = getResources()
                    .getString(R.string.group_summary, selectedStudentGroup);
        }

        findPreference(key_student_group)
                .setSummary(studentGroupSummary);

        String selectedTimetablePeriod = sharedPreferences.getString(key_timetable_period, null);
        String timetablePeriodSummary = getSummaryForSelectedPeriod(selectedTimetablePeriod);

        findPreference(key_timetable_period)
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

            return getString(R.string.meal_types_selected_summary, summary.substring(INCLUDED_MEALS_SEPARATOR.length()));
        } else {
            return getString(R.string.no_meal_types_selected_summary);
        }
    }
}
