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

    private final String CAMPUSRAVITA_INCLUDED_MEALS = "campusravita_included_meals";
    private final String PIRTERIA_INCLUDED_MEALS = "pirteria_included_meals";

    public SettingsFragment() {
        // Required empty public constructor
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

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        updateSummaries(PreferenceManager.getDefaultSharedPreferences(getActivity()));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummaries(sharedPreferences);
    }

    private void updateSummaries(SharedPreferences sharedPreferences) {
        Set<String> campusravitaIncludedMeals = sharedPreferences.getStringSet(CAMPUSRAVITA_INCLUDED_MEALS,
                new HashSet<String>());

        findPreference(CAMPUSRAVITA_INCLUDED_MEALS)
                .setSummary(getSummaryForSelectedListItems(campusravitaIncludedMeals,
                        R.array.campusravita_meals, R.array.campusravita_meals_readable));

        Set<String> pirteriaIncludedMeals = sharedPreferences.getStringSet(PIRTERIA_INCLUDED_MEALS,
                new HashSet<String>());

        findPreference(PIRTERIA_INCLUDED_MEALS)
                .setSummary(getSummaryForSelectedListItems(pirteriaIncludedMeals,
                        R.array.pirteria_meals, R.array.pirteria_meals_readable));
    }

    private String getSummaryForSelectedListItems(Set<String> selectedItems,
                                                  int normalArrayId, int readableArrayId) {
        if (selectedItems.size() > 0) {
            String separator = ", ";
            StringBuilder summary = new StringBuilder();

            for (String selectedItem : selectedItems) {
                String readableType = MealTypes
                        .getReadableMealType(getActivity(), normalArrayId,
                                readableArrayId, selectedItem);

                summary.append(separator).append(readableType);
            }

            return getString(R.string.prefix_meal_types_selected, summary.substring(separator.length()));
        } else {
            return getString(R.string.no_meal_types_selected);
        }
    }
}
